package com.plueone.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.plueone.server.models.MatchingScore;
import com.plueone.server.models.Preference;
import com.plueone.server.repo.MatchingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Service
public class MatchingService {

    @Autowired
    private MatchingRepository matchRepo;

    public Optional<List<String>> getMatchedProfiles(String userId, Preference preference) {

        List<String> userIds = matchRepo.getMatchedUsersByProfile(userId, preference);

        if (userIds.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(userIds);
        }
    }

    public Optional<List<MatchingScore>> getMatchedInterestsAndSubs(String userId) {

        List<MatchingScore> matches = matchRepo.getMatchesByInterestAndSubInterest(userId);

        if (matches.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(matches);
        }
    }

    public Optional<List<String>> getMatchedUsersByProfilesAndInterests(String userId, Preference preference) {
        List<String> commonIds = matchRepo.getMatchedInterestsAndProfile(userId, preference);

        if (commonIds.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(commonIds);
        }

    }

    public JsonArray toJsonArray(List<String> userIds) {

        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (String id : userIds) {

            JsonObject json = Json.createObjectBuilder()
                    .add("userId", id)
                    .build();

            jArr.add(json);
        }
        return jArr.build();
    }

}
