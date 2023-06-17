package com.plueone.server.repo;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.plueone.server.models.Answer;
import com.plueone.server.models.Image;
import com.plueone.server.models.Interest;
import com.plueone.server.models.Language;
import com.plueone.server.models.Personality;
import com.plueone.server.models.Preference;
import com.plueone.server.models.Profile;
import com.plueone.server.models.Subinterest;
import com.plueone.server.models.JwtAuth.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoInsertion {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        public static final String INSERT_USER_SQL = "insert into user (user_id, name, phone, email) values (?,?,?,?)"
                        +
                        "on duplicate key update name = values (name), phone = values (phone)";

        public static final String INSERT_PROFILE_SQL = "insert into profile (user_id, gender, race_id, birth_year, age, height, weight, location, diet_id, horoscope_id, job, religion, education_id, marital_status, relationship_goal, smoking, drinking, exercise)"
                        +
                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
                        "on duplicate key update gender = VALUES(gender), race_id = VALUES(race_id), birth_year = VALUES(birth_year), age = VALUES(age),"
                        +
                        "height = VALUES(height), weight = VALUES(weight), location = VALUES(location), diet_id = VALUES(diet_id),"
                        +
                        "horoscope_id = VALUES(horoscope_id), job = VALUES(job), religion = VALUES(religion), education_id = VALUES(education_id), marital_status = VALUES(marital_status),"
                        +
                        "relationship_goal = VALUES(relationship_goal), smoking = VALUES(smoking), drinking = VALUES(drinking), exercise = VALUES(exercise)";

        private static final String INSERT_USER_PREFERENCE_SQL = "insert into preference (user_id, race_pref_id, gender_pref, diet_pref_id, minHeight, maxHeight, minAge, maxAge) values (?, ?, ?, ?, ?, ?, ?, ?)"
                        +
                        "on duplicate key update race_pref_id = VALUES (race_pref_id), gender_pref = VALUES (gender_pref)"
                        +
                        "diet_pref_id = VALUES(diet_pref_id), minHeight = VALUES (minHeight), maxHeight = VALUES (maxHeight)"
                        +
                        "minAge = VALUES(minAge), maxAge = VALUES (maxAge)";

        private static final String INSERT_USER_IMAGES_SQL = "insert into images (user_id, url) values (?, ?)";

        private static final String INSERT_USER_INTEREST_SQL = "insert into userInterest (user_id, interest_id) values (?, ?)";

        private static final String INSERT_USER_SUB_INTEREST_SQL = "insert into userSubinterest (user_id, sub_interest_id) values (?, ?)";

        private static final String INSERT_USER_PERSONALITY_SQL = "insert into userPersonality (user_id, personality_id) values (?, ?)";

        private static final String INSERT_USER_ANSWERS_SQL = "insert into userPrompt (user_id, prompt_id, answer) values (?, ?, ?)";

        private static final String EDIT_USER_ANSWERS_SQL = "update userPrompt set answer = ? where id = ?";

        private static final String INSERT_USER_LANGUAGE_SQL = "insert into userLanguage (user_id, language_id) values (?, ?)";

        // public String createOrUpdateUser(String name, String phoneNum, String email)
        // {

        // User user = new User();

        // user.setName(name);
        // user.setPhoneNum(phoneNum);
        // user.setEmail(email);

        // String userId = UUID.randomUUID().toString().substring(0, 6);
        // user.setUserId(userId);

        // int rowUpdated = jdbcTemplate.update(INSERT_USER_SQL, user.getUserId(),
        // user.getName(),
        // user.getPhoneNum(),
        // user.getEmail());

        // System.out.printf(">>>>>rows updated: %d\n", rowUpdated);

        // return userId;

        // }

        // insert into profile (user_id, gender, race_id, birthyear, age, height,
        // weight, location, diet_id, horoscope_id, job, religion, education_id,
        // marital_status, relationship_goal, smoking, drinking, exercise)

        public int createOrUpdateProfile(String userId, Profile profile) {

                int rowUpdated = jdbcTemplate.update(INSERT_PROFILE_SQL, userId, profile.getGender(),
                                profile.getRaceId(),
                                profile.getBirthYear(), profile.getAge(), profile.getHeight(), profile.getWeight(),
                                profile.getLocation(), profile.getDietId(), profile.getHoroscopeId(), profile.getJob(),
                                profile.getReligion(), profile.getEducationId(), profile.getMaritalStatus(),
                                profile.getRelationshipGoal(), profile.getSmoking(), profile.getDrinking(),
                                profile.getExercise());

                System.out.printf(">>>>>createOrUpdateProfile rows updated: %d\n", rowUpdated);

                return rowUpdated;

        }

        // insert into preference (user_id, race_pref_id, gender_pref, diet_pref_id,
        // minHeight, maxHeight, minAge, maxAge) values (?, ?, ?, ?, ?, ?, ?, ?) on
        // duplicate key update"

        public int createOrUpdatePreference(String userId, Preference preference) {

                int rowUpdated = jdbcTemplate.update(INSERT_USER_PREFERENCE_SQL, userId, preference.getRacePrefId(),
                                preference.getGenderPref(),
                                preference.getDietPrefId(), preference.getMinHeight(), preference.getMaxHeight(),
                                preference.getMinAge(), preference.getMaxAge());

                System.out.printf(">>>>>createOrUpdateProfile rows updated: %d\n", rowUpdated);

                return rowUpdated;

        }

        // can be list
        public int insertImages(Image image) {
                int rowUpdated = jdbcTemplate.update(INSERT_USER_IMAGES_SQL, image.getUserId(),
                                image.getUrl());

                System.out.printf(">>>>>insertImages rows updated: %d\n", rowUpdated);

                return rowUpdated;

        }

        public int[] insertUserInterest(String userId, List<Interest> interests) {

                List<Object[]> params = interests.stream()
                                .map(interest -> new Object[] { userId, interest.getInterestId() })
                                .collect(Collectors.toList());

                int[] added = jdbcTemplate.batchUpdate(INSERT_USER_INTEREST_SQL, params);

                System.out.printf(">>>>>insertUserInterest rows updated: %d\n", added.length);

                return added;

        }

        public int[] insertUserSubInterest(String userId, List<Subinterest> subInterests) {

                List<Object[]> params = subInterests.stream()
                                .map(sub -> new Object[] { userId, sub.getSubInterestId() })
                                .collect(Collectors.toList());

                int[] added = jdbcTemplate.batchUpdate(INSERT_USER_SUB_INTEREST_SQL, params);

                System.out.printf(">>>>>insertUserSubInterest rows updated: %d\n", added.length);

                return added;

        }

        public int[] insertUserPersonality(String userId, List<Personality> personalities) {

                List<Object[]> params = personalities.stream()
                                .map(p -> new Object[] { userId, p.getPersonalityId() })
                                .collect(Collectors.toList());

                int[] added = jdbcTemplate.batchUpdate(INSERT_USER_PERSONALITY_SQL, params);

                System.out.printf(">>>>> insertUserPersonality rows updated: %d\n", added.length);

                return added;

        }

        public int[] insertUserAnswers(String userId, List<Answer> answers) {

                List<Object[]> params = answers.stream()
                                .map(ans -> new Object[] { userId, ans.getPromptId(), ans.getAnswer() })
                                .collect(Collectors.toList());

                int[] added = jdbcTemplate.batchUpdate(INSERT_USER_ANSWERS_SQL, params);

                System.out.printf(">>>>> insertUserAnswers rows updated: %d\n", added.length);

                return added;

        }

        public int[] insertUserLanguages(String userId, List<Language> languages) {

                List<Object[]> params = languages.stream()
                                .map(l -> new Object[] { userId, l.getLanguageId() })
                                .collect(Collectors.toList());

                int[] added = jdbcTemplate.batchUpdate(INSERT_USER_LANGUAGE_SQL, params);

                System.out.printf(">>>>> insertUserLanguages rows updated: %d\n", added.length);

                return added;

        }

        public int editUserAnswer(Answer answer) {

                int added = jdbcTemplate.update(EDIT_USER_ANSWERS_SQL, answer.getAnswer(), answer.getId());

                System.out.printf(">>>>> editUserAnswer rows updated: %d\n", added);

                return added;

        }

}
