package com.plueone.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.plueone.server.models.CompleteProfile;
import com.plueone.server.models.Interest;
import com.plueone.server.models.MatchingScore;
import com.plueone.server.models.Preference;
import com.plueone.server.models.Subinterest;
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

    @Autowired
    private InfoRetrievalService infoRtrvSvc;

    public Double getInterestMatchingScores(String currUserId, String otherUserId) {

        Optional<List<Interest>> optUserInterests = infoRtrvSvc.getUserInterest(currUserId);
        Optional<List<Interest>> optRequesteeInterests = infoRtrvSvc.getUserInterest(otherUserId);

        if (optUserInterests.isPresent() && optRequesteeInterests.isPresent()) {
            List<Integer> userInterestIds = optUserInterests.get().stream()
                    .map(i -> i.getInterestId())
                    .toList();

            List<Integer> requesteeInterestIds = optRequesteeInterests.get().stream()
                    .map(i -> i.getInterestId())
                    .toList();

            System.out.printf(">>>>>>>>>%s\n", userInterestIds.toString());
            System.out.printf(">>>>>>>>>%s\n", requesteeInterestIds.toString());

            double interestMatchScore = calculateMatchingScore(userInterestIds, requesteeInterestIds);
            return interestMatchScore;
        } else {
            return 0.0;
        }

    }

    public Double getSubInterestMatchingScores(String currUserId, String otherUserId) {

        Optional<List<Subinterest>> optUserSubInterests = infoRtrvSvc.getUserSubInterest(currUserId);
        Optional<List<Subinterest>> optRequesteeSubInterests = infoRtrvSvc.getUserSubInterest(otherUserId);

        if (optUserSubInterests.isPresent() && optRequesteeSubInterests.isPresent()) {
            List<Integer> userSubInterestIds = optUserSubInterests.get().stream()
                    .map(i -> i.getSubInterestId())
                    .toList();

            List<Integer> requesteeSubInterestIds = optRequesteeSubInterests.get().stream()
                    .map(i -> i.getSubInterestId())
                    .toList();

            double subMatchScore = calculateMatchingScore(userSubInterestIds, requesteeSubInterestIds);
            return subMatchScore;
        } else {
            return 0.0;
        }
    }

    public static double calculateMatchingScore(List<Integer> currUserList, List<Integer> otherUserList) {

        List<Integer> commonInterests = new ArrayList<>(currUserList);
        commonInterests.retainAll(otherUserList);
        double interestMatchScore = (double) commonInterests.size() / currUserList.size();

        System.out.printf(">>>>>>>>>common: %s\n", commonInterests.toString());
        System.out.printf(">>>>>>>>>common: %.1f\n", interestMatchScore * 100);

        return interestMatchScore * 100;

    }

    public Optional<List<CompleteProfile>> getMatchedProfiles(String userId, Preference preference) {

        List<CompleteProfile> matchedProfiles = matchRepo.getMatchedUsersByProfile(userId, preference).stream()
                .map(id -> {
                    CompleteProfile cp = new CompleteProfile();
                    cp.setUserId(id);
                    cp.setInterestScore(getInterestMatchingScores(userId, id));
                    cp.setSubInterestScore(getSubInterestMatchingScores(userId, id));
                    return cp;
                }).toList();

        if (matchedProfiles.isEmpty()) {
            return Optional.empty();

        } else {
            return Optional.of(matchedProfiles);
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

    // public JsonArray toJsonArray(List<String> userIds) {

    //     JsonArrayBuilder jArr = Json.createArrayBuilder();

    //     for (String id : userIds) {

    //         JsonObject json = Json.createObjectBuilder()
    //                 .add("userId", id)
    //                 .build();

    //         jArr.add(json);
    //     }
    //     return jArr.build();
    // }

    public JsonArray toJsonArray(List<CompleteProfile> profiles) {

        JsonArrayBuilder jArr = Json.createArrayBuilder();

        for (CompleteProfile cp : profiles) {

            JsonObject json = Json.createObjectBuilder()
                    .add("userId", cp.getUserId())
                    .add("interestScore", cp.getInterestScore())
                    .add("subInterestScore", cp.getSubInterestScore())
                    .build();
            jArr.add(json);

        }
        return jArr.build();
    }

}
