package com.plueone.server.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDeletion {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String DEL_USER_SQL = "delete from user where user_id = ? ";

    public static final String DEL_PROFILE_SQL = "delete from profile where user_id = ?";

    private static final String DEL_USER_PREFERENCE_SQL = "delete from preference where user_id = ?";

    private static final String DEL_USER_IMAGES_BY_ID_SQL = "delete from images where image_id = ?";

    private static final String DEL_USER_INTEREST_BY_ID_SQL = "delete from userInterest where user_id = ? and interest_id = ?";

    private static final String DEL_USER_SUB_INTEREST_BY_ID_SQL = "delete from userSubinterest where user_id = ? and sub_interest_id = ?";

    private static final String DEL_USER_SUB_INTEREST_BY_INTEREST_ID_SQL = "delete from userSubinterest where user_id = ? and interest_id = ?";

    private static final String DEL_USER_PERSONALITY_BY_ID_SQL = "delete from userPersonality where user_id = ? and personality_id = ?";

    private static final String DEL_USER_ANSWERS_BY_ID_SQL = "delete from userPrompt where user_id = ? and prompt_id = ?";

    private static final String DEL_ALL_USER_ANSWERS_SQL = "delete from userPrompt where user_id = ?";

    private static final String DEL_USER_LANGUAGE_BY_ID_SQL = "delete from userLanguage where user_id = ? and language_id = ?";

    public int deleteUser(String userId) {
        return jdbcTemplate.update(DEL_USER_SQL, userId);
    }

    public int deleteProfile(String userId) {
        return jdbcTemplate.update(DEL_PROFILE_SQL, userId);
    }

    public int deletePreference(String userId) {
        return jdbcTemplate.update(DEL_USER_PREFERENCE_SQL, userId);
    }

    public int deleteImage(Integer imageId) {
        return jdbcTemplate.update(DEL_USER_IMAGES_BY_ID_SQL, imageId);
    }

    public int deleteInterest(String userId, Integer interestId) {

        int rowAffected = 0;

        rowAffected += jdbcTemplate.update(DEL_USER_SUB_INTEREST_BY_INTEREST_ID_SQL, userId, interestId);
        rowAffected += jdbcTemplate.update(DEL_USER_INTEREST_BY_ID_SQL, userId, interestId);
        return rowAffected;
    }

    public int deleteSubInterest(String userId, Integer subInterestId) {
        return jdbcTemplate.update(DEL_USER_SUB_INTEREST_BY_ID_SQL, userId, subInterestId);
    }

    public int deletePersonality(String userId, Integer personalityId) {
        return jdbcTemplate.update(DEL_USER_PERSONALITY_BY_ID_SQL, userId, personalityId);
    }

    public int deleteAnswer(String userId, Integer promptId) {
        return jdbcTemplate.update(DEL_USER_ANSWERS_BY_ID_SQL, userId, promptId);
    }

    public int deleteAllAnswers(String userId) {
        return jdbcTemplate.update(DEL_ALL_USER_ANSWERS_SQL, userId);
    }

    public int deleteLanguage(String userId, Integer languageId) {
        return jdbcTemplate.update(DEL_USER_LANGUAGE_BY_ID_SQL, userId, languageId);
    }
}
