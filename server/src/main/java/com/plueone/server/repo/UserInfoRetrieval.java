package com.plueone.server.repo;

import java.util.ArrayList;
import java.util.List;

import com.plueone.server.models.Answer;
import com.plueone.server.models.Image;
import com.plueone.server.models.Interest;
import com.plueone.server.models.Language;
import com.plueone.server.models.Personality;
import com.plueone.server.models.Preference;
import com.plueone.server.models.Profile;
import com.plueone.server.models.Race;
import com.plueone.server.models.Subinterest;
import com.plueone.server.models.JwtAuth.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRetrieval {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private static final String GET_USER_INFO_SQL = "select * from user where user_id = ?";

        private static final String CHECK_USERID_EXISTS = "SELECT EXISTS(SELECT 1 FROM user WHERE user_id = ?) AS user_exists";

        private static final String CHECK_USER_EMAIL_EXISTS = "SELECT EXISTS(SELECT 1 FROM user WHERE email = ?) AS email_exists";

        private static final String GET_USER_PROFILE_SQL = "SELECT p.user_id, u.name AS name, p.gender, p.birth_year, p.age, h.horoscope_id AS horoscope_id, h.name AS horoscope_name, p.weight, p.height, r.race_id AS race_id, r.name AS race_name, p.location, p.job, p.religion, e.education_id AS education_id, e.name AS education_name, p.marital_status, p.relationship_goal, p.smoking, p.drinking, p.exercise, d.diet_id AS diet_id, d.name AS diet_name "
                        +
                        "FROM profile p " +
                        "JOIN user u ON u.user_id = p.user_id " +
                        "JOIN race r ON r.race_id = p.race_id " +
                        "JOIN diet d ON d.diet_id = p.diet_id " +
                        "JOIN horoscope h ON h.horoscope_id = p.horoscope_id " +
                        "JOIN education e ON e.education_id = p.education_id " +
                        "WHERE p.user_id = ?";

        private static final String GET_USER_IMAGES_SQL = "select * from images where user_id = ?";

        private static final String GET_USER_INTEREST_SQL = "select u.user_id, i.interest_id, i.name as interest_name from user u "
                        +
                        "join userInterest ui on u.user_id = ui.user_id " +
                        "join interest i on i.interest_id = ui.interest_id " +
                        "where u.user_id = ?";

        private static final String GET_USER_SUB_INTEREST_SQL = "select u.user_id, s.interest_id, s.sub_interest_id, s.name as sub_interest_name "
                        +
                        "from user u join userSubinterest si on u.user_id = si.user_id " +
                        "join subInterest s on s.sub_interest_id = si.sub_interest_id " +
                        "where u.user_id = ?";

        private static final String GET_USER_PERSONALITY_SQL = "select u.user_id, p.personality_id, p.name as personality_type "
                        +
                        "from user u join userPersonality up on u.user_id = up.user_id " +
                        "join personality p on p.personality_id = up.personality_id " +
                        "where u.user_id = ?";

        private static final String GET_USER_PREFERENCE_SQL = "select pr.user_id, pr.preference_id, r.race_id as race_pref_id, r.name as race_preference, pr.gender_pref, "
                        +
                        "d.diet_id as diet_pref_id, d.name as diet_preference, pr.minHeight, pr.maxHeight, pr.minAge, pr.maxAge "
                        +
                        "from user u join preference pr on u.user_id = pr.user_id " +
                        "join race r on pr.race_pref_id = r.race_id " +
                        "join diet d on pr.diet_pref_id = d.diet_id " +
                        "where u.user_id = ?";

        private static final String GET_USER_ANSWERS_SQL = "select up.id, up.user_id, up.prompt_id, p.name as prompt_question, up.answer "
                        +
                        "from userPrompt up join prompt p on p.prompt_id = up.prompt_id " +
                        "where user_id = ?";

        private static final String GET_USER_LANGUAGE_SQL = "select u.user_id, l.language_id, l.name as language_name "
                        +
                        "from user u join userLanguage ul on ul.user_id = u.user_id " +
                        "join language l on l.language_id = ul.language_id " +
                        "where u.user_id = ?";

        public Integer findUserByEmail(String email) { 

                int count = jdbcTemplate.queryForObject(CHECK_USER_EMAIL_EXISTS, (rs, rowNum) -> rs.getInt(1), email);

                return count > 0 ? 1 : 0;
        }

        public Integer checkIfUserIdExists(String userId) {

                int count = jdbcTemplate.queryForObject(CHECK_USERID_EXISTS, (rs, rowNum) -> rs.getInt(1), userId);

                return count > 0 ? 1 : 0;

        }

        public SqlRowSet getUserProfileById(String userId) {

                return jdbcTemplate.queryForRowSet(GET_USER_PROFILE_SQL, userId);

                // return jdbcTemplate.queryForObject(GET_USER_PROFILE_SQL,
                // BeanPropertyRowMapper.newInstance(Profile.class),
                // userId);

        }

        public SqlRowSet getUserPreference(String userId) {

                return jdbcTemplate.queryForRowSet(GET_USER_PREFERENCE_SQL, userId);

                // return jdbcTemplate.queryForObject(GET_USER_PREFERENCE_SQL,
                // BeanPropertyRowMapper.newInstance(Preference.class),
                // userId);
        }

        public List<Image> getUserImages(String userId) {
                return jdbcTemplate.query(GET_USER_IMAGES_SQL, BeanPropertyRowMapper.newInstance(Image.class),
                                userId);
        }

        public List<Interest> getUserInterest(String userId) {
                return jdbcTemplate.query(GET_USER_INTEREST_SQL, BeanPropertyRowMapper.newInstance(Interest.class),
                                userId);
        }

        public List<Subinterest> getUserSubInterest(String userId) {
                return jdbcTemplate.query(GET_USER_SUB_INTEREST_SQL,
                                BeanPropertyRowMapper.newInstance(Subinterest.class),
                                userId);
        }

        public List<Personality> getUserPersonality(String userId) {
                return jdbcTemplate.query(GET_USER_PERSONALITY_SQL,
                                BeanPropertyRowMapper.newInstance(Personality.class),
                                userId);
        }

        public List<Answer> getUserPromptAnswers(String userId) {
                return jdbcTemplate.query(GET_USER_ANSWERS_SQL, BeanPropertyRowMapper.newInstance(Answer.class),
                                userId);
        }

        public List<Language> getUserLanguages(String userId) {
                return jdbcTemplate.query(GET_USER_LANGUAGE_SQL, BeanPropertyRowMapper.newInstance(Language.class),
                                userId);
        }

}
