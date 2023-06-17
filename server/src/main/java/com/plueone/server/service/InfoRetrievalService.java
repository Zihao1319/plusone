package com.plueone.server.service;

import java.util.List;
import java.util.Optional;

import com.plueone.server.models.Answer;
import com.plueone.server.models.CompleteProfile;
import com.plueone.server.models.Image;
import com.plueone.server.models.Interest;
import com.plueone.server.models.Language;
import com.plueone.server.models.Personality;
import com.plueone.server.models.Preference;
import com.plueone.server.models.Profile;
import com.plueone.server.models.Subinterest;
import com.plueone.server.repo.UserInfoRetrieval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class InfoRetrievalService {

    @Autowired
    private UserInfoRetrieval userInfoRvl;

    public Integer checkIfUserEmailExists(String email) {
        return userInfoRvl.findUserByEmail(email);
    }

    public Integer checkIfUserIdExists(String userId) {
        return userInfoRvl.checkIfUserIdExists(userId);
    }

    public Optional<CompleteProfile> getCompleteProfile(String userId) {

        int iExists = userInfoRvl.checkIfUserIdExists(userId);

        if (iExists == 0) {
            return Optional.empty();
        }

        CompleteProfile cProfile = new CompleteProfile();

        Optional<Profile> optProfile = getUserProfileById(userId);
        Optional<Preference> optPreference = getUserPreference(userId);
        Optional<List<Language>> optLang = getUserLanguages(userId);
        Optional<List<Interest>> optInterests = getUserInterest(userId);
        Optional<List<Subinterest>> optSubinterests = getUserSubInterest(userId);
        Optional<List<Personality>> optPersonalities = getUserPersonality(userId);
        Optional<List<Image>> optImages = getUserImages(userId);
        Optional<List<Answer>> optAnswers = getUserPromptAnswers(userId);

        cProfile.setUserId(userId);

        if (optProfile.isPresent()) {
            cProfile.setProfile(optProfile.get());
        }

        if (optPreference.isPresent()) {
            cProfile.setPreference(optPreference.get());
        }

        if (optLang.isPresent()) {
            cProfile.setLanguages(optLang.get());
        }

        if (optInterests.isPresent()) {
            cProfile.setInterests(optInterests.get());
        }

        if (optSubinterests.isPresent()) {
            cProfile.setSubInterests(optSubinterests.get());
        }

        if (optPersonalities.isPresent()) {
            cProfile.setPersonalities(optPersonalities.get());
        }

        if (optImages.isPresent()) {
            cProfile.setImages(optImages.get());
        }

        if (optAnswers.isPresent()) {
            cProfile.setAnswers(optAnswers.get());
        }

        return Optional.of(cProfile);

    }

    public Optional<Profile> getUserProfileById(String userId) {

        SqlRowSet rs = userInfoRvl.getUserProfileById(userId);

        if (rs.first()) {
            return Optional.of(Profile.populate(rs));
        }
        return Optional.empty();
    }

    public Optional<Preference> getUserPreference(String userId) {

        SqlRowSet rs = userInfoRvl.getUserPreference(userId);

        if (rs.first()) {
            return Optional.of(Preference.populate(rs));
        }
        return Optional.empty();
    }

    public Optional<List<Image>> getUserImages(String userId) {

        List<Image> result = userInfoRvl.getUserImages(userId);

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result);
        }

    }

    public Optional<List<Interest>> getUserInterest(String userId) {

        List<Interest> result = userInfoRvl.getUserInterest(userId);

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result);
        }
    }

    public Optional<List<Subinterest>> getUserSubInterest(String userId) {

        List<Subinterest> result = userInfoRvl.getUserSubInterest(userId);

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result);
        }

    }

    public Optional<List<Personality>> getUserPersonality(String userId) {

        List<Personality> result = userInfoRvl.getUserPersonality(userId);

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result);
        }

    }

    public Optional<List<Answer>> getUserPromptAnswers(String userId) {

        List<Answer> result = userInfoRvl.getUserPromptAnswers(userId);

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result);
        }

    }

    public Optional<List<Language>> getUserLanguages(String userId) {

        List<Language> result = userInfoRvl.getUserLanguages(userId);

        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result);
        }

    }

}
