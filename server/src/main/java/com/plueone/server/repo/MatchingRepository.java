package com.plueone.server.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.plueone.server.models.MatchingScore;
import com.plueone.server.models.Preference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class MatchingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String FIND_USERS_WITH_INTERESTS_AND_SUBINTERESTS = "SELECT u.user_id, u.name, " +
            "CASE WHEN ui.totalInterests > 0 THEN ROUND((ui.score / refInterest.score) * 100, 2) ELSE 0 END AS interest_matching_score, "
            +
            "CASE WHEN us.totalSubInterests > 0 THEN ROUND((us.score / refSubInterest.score) * 100, 2) ELSE 0 END AS subInterest_matching_score "
            +
            "FROM user u " +
            "LEFT JOIN (" +
            "  SELECT user_id, COUNT(interest_id) AS score, COUNT(DISTINCT interest_id) AS totalInterests " +
            "  FROM userInterest " +
            "  WHERE user_id IN (?)" +
            "  GROUP BY user_id" +
            ") refInterest ON refInterest.user_id = ? " +
            "LEFT JOIN (" +
            "  SELECT ui.user_id, COUNT(ui.interest_id) AS score, COUNT(DISTINCT ui.interest_id) AS totalInterests " +
            "  FROM userInterest ui " +
            "  WHERE ui.interest_id IN (" +
            "    SELECT interest_id " +
            "    FROM userInterest " +
            "    WHERE user_id = ?" +
            "  ) AND ui.user_id != ? " +
            "  GROUP BY ui.user_id" +
            ") ui ON u.user_id = ui.user_id " +
            "LEFT JOIN (" +
            "  SELECT user_id, COUNT(sub_interest_id) AS score, COUNT(DISTINCT sub_interest_id) AS totalSubinterests " +
            "  FROM userSubinterest " +
            "  WHERE user_id IN (?)" +
            "  GROUP BY user_id" +
            ") refSubInterest ON refSubInterest.user_id = ? " +
            "LEFT JOIN (" +
            "  SELECT us.user_id, COUNT(us.sub_interest_id) AS score, COUNT(DISTINCT us.sub_interest_id) AS totalSubinterests "
            +
            "  FROM userSubinterest us " +
            "  WHERE us.sub_interest_id IN (" +
            "    SELECT sub_interest_id " +
            "    FROM userSubinterest " +
            "    WHERE user_id = ?" +
            "  ) AND us.user_id != ? " +
            "  GROUP BY us.user_id" +
            ") us ON u.user_id = us.user_id " +
            "WHERE u.user_id != ? " +
            "AND ui.score / refInterest.score !=0 " +
            "ORDER BY interest_matching_score DESC, subInterest_matching_score DESC";

    public List<String> getMatchedInterestsAndProfile(String userId, Preference preference) {

        List<String> matchedProfileUserIds = getMatchedUsersByProfile(userId, preference);
        System.out.printf(">>>>>>> matchedprofiles: %s\n", matchedProfileUserIds.toString());

        List<String> matchedInterestUserIds = getMatchesByInterestAndSubInterest(userId).stream()
                .map(MatchingScore::getUserId)
                .collect(Collectors.toList());

        List<String> commonUserIds = new ArrayList<>(matchedProfileUserIds);
        commonUserIds.retainAll(matchedInterestUserIds);

        return commonUserIds;

    }

    // get all the userIds based on profile search only
    public List<String> getMatchedUsersByProfile(String userId, Preference preference) {

        if (null == preference.getPreferenceId()) {
            return Collections.emptyList();
        }

        System.out.printf(">>>>>>> preference: %s\n", preference.toString());

        String query = generateSearchQuery(userId, preference);

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(query);

        List<String> userIds = new ArrayList<String>();

        while (rs.next()) {
            userIds.add(rs.getString("user_id"));
        }

        return userIds;

    }

    public String generateSearchQuery(String userId, Preference preference) {

        String gender = preference.getGenderPref();
        String race = preference.getRacePreference();
        String diet = preference.getDietPreference();
        Integer minAge = preference.getMinAge();
        Integer maxAge = preference.getMaxAge();
        Integer minHeight = preference.getMinHeight();
        Integer maxHeight = preference.getMaxHeight();

        String baseQuery = "Select user_id from profile p where 1=1";

        if (gender != null && !gender.equals("everyone")) {
            baseQuery += " AND gender = " + "'" + gender + "'";
        }

        if (race != null) {
            baseQuery += " AND race_id = (select race_id from race where name = '" + race + "')";
        }

        if (diet != null && !diet.equals("no preference")) {
            baseQuery += " AND diet_id = (select diet_id from diet where name = '" + diet + "')";
        }

        if (minAge != null) {
            baseQuery += " AND age >=" + minAge;
        }

        if (maxAge != null) {
            baseQuery += " AND age <=" + maxAge;
        }

        if (minHeight != null) {
            baseQuery += " AND height >=" + minHeight;
        }

        if (maxHeight != null) {
            baseQuery += " AND height <=" + maxHeight;
        }

        baseQuery += " AND USER_ID != " + "'" + userId + "'";

        return baseQuery;
    }

    // get only the interests and subinterests matches
    public List<MatchingScore> getMatchesByInterestAndSubInterest(String userId) {

        final List<MatchingScore> matches = new LinkedList<>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(FIND_USERS_WITH_INTERESTS_AND_SUBINTERESTS, userId, userId,
                userId, userId, userId, userId, userId, userId, userId);

        while (rs.next()) {
            MatchingScore match = new MatchingScore();
            match.setUserId(rs.getString("user_id"));
            match.setName(rs.getString("name"));
            match.setInterestScore(rs.getDouble("interest_matching_score"));
            match.setSubInterestScore(rs.getDouble("subInterest_matching_score"));
            matches.add(match);
        }

        return matches;
    }

}
