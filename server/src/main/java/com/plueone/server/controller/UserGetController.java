package com.plueone.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.plueone.server.models.Answer;
import com.plueone.server.models.CompleteProfile;
import com.plueone.server.models.Diet;
import com.plueone.server.models.Education;
import com.plueone.server.models.Friendship;
import com.plueone.server.models.Horoscope;
import com.plueone.server.models.Image;
import com.plueone.server.models.Interest;
import com.plueone.server.models.Language;
import com.plueone.server.models.MatchingScore;
import com.plueone.server.models.Personality;
import com.plueone.server.models.Preference;
import com.plueone.server.models.Prompt;
import com.plueone.server.models.Race;
import com.plueone.server.models.Subinterest;
import com.plueone.server.models.JwtAuth.User;
import com.plueone.server.repo.OptionsRetrieval;
import com.plueone.server.service.FriendshipService;
import com.plueone.server.service.InfoRetrievalService;
import com.plueone.server.service.MatchingService;
import com.plueone.server.service.openAI.OpenAiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@RestController
@RequestMapping(path = "/api")
public class UserGetController {

        @Autowired
        private InfoRetrievalService infoRtrvSvc;

        @Autowired
        private OptionsRetrieval optRtrv;

        @Autowired
        private MatchingService matchSvc;

        @Autowired
        private FriendshipService friendshipSvc;

        @GetMapping(path = "/login")
        public ResponseEntity<String> userLogin(@RequestBody User user) {
                return null;
        }

        @GetMapping(path = "/options")
        public ResponseEntity<String> getAllOptions() {

                List<Diet> dietOptions = optRtrv.getDietOptions();
                List<Education> eduOptions = optRtrv.getEducationOptions();
                List<Horoscope> horosOptions = optRtrv.getHosorscopeOptions();
                List<Interest> interestOptions = optRtrv.getInterestOptions();
                List<Subinterest> subOptions = optRtrv.getSubInterestOptions();
                List<Language> langOptions = optRtrv.getLanguageOptions();
                List<Personality> personalityOptions = optRtrv.getPersonalityOptions();
                List<Prompt> promptOptions = optRtrv.getPromptOptions();
                List<Race> raceOptions = optRtrv.getRaceOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("diets", Diet.toJsonArray(dietOptions))
                                .add("education", Education.toJsonArray(eduOptions))
                                .add("horoscopes", Horoscope.toJsonArray(horosOptions))
                                .add("interests", Interest.toJsonArr(interestOptions))
                                .add("subinterests", Subinterest.toJsonArr(subOptions))
                                .add("languages", Language.toJsonArr(langOptions))
                                .add("personalities", Personality.toJsonArr(personalityOptions))
                                .add("prompts", Prompt.toJsonArray(promptOptions))
                                .add("races", Race.toJsonArray(raceOptions))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // get user profile
        @GetMapping(path = "/profile/{userId}")
        public ResponseEntity<String> getUserProfile(@PathVariable String userId) {

                int userExists = infoRtrvSvc.checkIfUserIdExists(userId);

                if (userExists == 0) {

                        JsonObject json = Json.createObjectBuilder()
                                        .add("message", "user %s not found".formatted(userId))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(json.toString());
                }

                Optional<CompleteProfile> cProfile = infoRtrvSvc.getCompleteProfile(userId);
                CompleteProfile cp = cProfile.get();
                JsonObject payload = CompleteProfile.toJson(cp);

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // get user preference
        @GetMapping(path = "/preference/{userId}")
        public ResponseEntity<String> getUserPreference(@PathVariable String userId) {

                int userExists = infoRtrvSvc.checkIfUserIdExists(userId);

                if (userExists == 0) {

                        JsonObject json = Json.createObjectBuilder()
                                        .add("message", "user %s not found".formatted(userId))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(json.toString());
                }

                Optional<Preference> cPref = infoRtrvSvc.getUserPreference(userId);
                Preference p = cPref.get();
                JsonObject payload = Preference.toJson(p);

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        };

        // get user language
        @GetMapping(path = "/language/{userId}")
        public ResponseEntity<String> getUserLanguage(@PathVariable String userId) {

                int userExists = infoRtrvSvc.checkIfUserIdExists(userId);

                if (userExists == 0) {

                        JsonObject json = Json.createObjectBuilder()
                                        .add("message", "user %s not found".formatted(userId))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(json.toString());
                }

                Optional<List<Language>> opt = infoRtrvSvc.getUserLanguages(userId);
                List<Language> lang = opt.get();

                JsonObject json = Json.createObjectBuilder()
                                .add("languages", Language.toJsonArr(lang))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(json.toString());

        };

        // get user personalities
        @GetMapping(path = "/personality/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getUserPersonality(@PathVariable String userId) {

                int userExists = infoRtrvSvc.checkIfUserIdExists(userId);

                if (userExists == 0) {

                        JsonObject json = Json.createObjectBuilder()
                                        .add("message", "user %s not found".formatted(userId))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(json.toString());
                }

                Optional<List<Personality>> opt = infoRtrvSvc.getUserPersonality(userId);

                if (opt.isPresent()) {
                        List<Personality> p = opt.get();
                        JsonObject json = Json.createObjectBuilder()
                                        .add("personalities", Personality.toJsonArr(p))
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(json.toString());
                } else {
                        JsonObject json = Json.createObjectBuilder()
                                        .add("personalities", "")
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(json.toString());

                }

        };

        // get user interests
        @GetMapping(path = "/interest/{userId}")
        public ResponseEntity<String> getUserInterest(@PathVariable String userId) {

                int userExists = infoRtrvSvc.checkIfUserIdExists(userId);

                if (userExists == 0) {

                        JsonObject json = Json.createObjectBuilder()
                                        .add("message", "user %s not found".formatted(userId))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(json.toString());
                }

                Optional<List<Interest>> opt = infoRtrvSvc.getUserInterest(userId);

                if (opt.isPresent()) {
                        List<Interest> p = opt.get();
                        JsonObject json = Json.createObjectBuilder()
                                        .add("interests", Interest.toJsonArr(p))
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(json.toString());
                } else {
                        JsonObject json = Json.createObjectBuilder()
                                        .add("interests", "")
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(json.toString());

                }

        };

        // get user subInterests
        @GetMapping(path = "/subinterest/{userId}")
        public ResponseEntity<String> getUserSubInterest(@PathVariable String userId) {

                int userExists = infoRtrvSvc.checkIfUserIdExists(userId);

                if (userExists == 0) {

                        JsonObject json = Json.createObjectBuilder()
                                        .add("message", "user %s not found".formatted(userId))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(json.toString());
                }

                Optional<List<Subinterest>> opt = infoRtrvSvc.getUserSubInterest(userId);
                List<Subinterest> p = opt.get();

                JsonObject json = Json.createObjectBuilder()
                                .add("subinterests", Subinterest.toJsonArr(p))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(json.toString());

        };

        // get user subInterests
        @GetMapping(path = "/answer/{userId}")
        public ResponseEntity<String> getUserAnswers(@PathVariable String userId) {

                int userExists = infoRtrvSvc.checkIfUserIdExists(userId);

                if (userExists == 0) {

                        JsonObject json = Json.createObjectBuilder()
                                        .add("message", "user %s not found".formatted(userId))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(json.toString());
                }

                Optional<List<Answer>> opt = infoRtrvSvc.getUserPromptAnswers(userId);
                List<Answer> p = opt.get();

                JsonObject json = Json.createObjectBuilder()
                                .add("answers", Answer.toJsonArr(p))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(json.toString());

        };

        // displaying diet options
        @GetMapping(path = "/options/diet")
        public ResponseEntity<String> getDietOptions() {

                List<Diet> options = optRtrv.getDietOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("diets", Diet.toJsonArray(options))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // displaying diet options
        @GetMapping(path = "/options/education")
        public ResponseEntity<String> getEducationOptions() {

                List<Education> options = optRtrv.getEducationOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("education", Education.toJsonArray(options))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // displaying horoscope options
        @GetMapping(path = "/options/horoscope")
        public ResponseEntity<String> getHoroscopeOptions() {

                List<Horoscope> options = optRtrv.getHosorscopeOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("horoscopes", Horoscope.toJsonArray(options))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // displaying interest options
        @GetMapping(path = "/options/interests")
        public ResponseEntity<String> getInterestOptions() {

                List<Interest> options = optRtrv.getInterestOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("interests", Interest.toJsonArr(options))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // displaying subinterest options
        @GetMapping(path = "/options/subinterests")
        public ResponseEntity<String> getSubInterestOptions() {

                List<Subinterest> options = optRtrv.getSubInterestOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("subinterests", Subinterest.toJsonArr(options))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // displaying language options
        @GetMapping(path = "/options/languages")
        public ResponseEntity<String> getLanguageOptions() {

                List<Language> options = optRtrv.getLanguageOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("languages", Language.toJsonArr(options))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // displaying personality options
        @GetMapping(path = "/options/personalities")
        public ResponseEntity<String> getPersonalityOptions() {

                List<Personality> options = optRtrv.getPersonalityOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("personalities", Personality.toJsonArr(options))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // displaying personality options
        @GetMapping(path = "/options/prompts")
        public ResponseEntity<String> getPromptQuestions() {

                List<Prompt> options = optRtrv.getPromptOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("prompts", Prompt.toJsonArray(options))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // displaying personality options
        @GetMapping(path = "/options/race")
        public ResponseEntity<String> getRaceOptions() {

                List<Race> options = optRtrv.getRaceOptions();

                JsonObject payload = Json.createObjectBuilder()
                                .add("races", Race.toJsonArray(options))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // get user images
        @GetMapping(path = "/images/{userId}")
        public ResponseEntity<String> getUserImages(@PathVariable String userId) {

                Optional<List<Image>> optImages = infoRtrvSvc.getUserImages(userId);

                List<Image> images = new LinkedList<>();

                if (optImages.isEmpty()) {

                        images = Collections.emptyList();

                        // JsonObject payload = Json.createObjectBuilder()
                        // .add("message", "image not found")
                        // .build();

                        // return ResponseEntity
                        // .status(404)
                        // .body(payload.toString());
                } else {
                        images = optImages.get();
                }

                JsonObject payload = Json.createObjectBuilder()
                                .add("images", Image.toJsonArr(images))
                                .build();

                return ResponseEntity
                                .status(200)
                                .body(payload.toString());

        }

        // displaying matches
        @GetMapping(path = "/matches/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getMatchedUsers(@PathVariable String userId) {

                int userExists = infoRtrvSvc.checkIfUserIdExists(userId);

                if (userExists == 0) {

                        JsonObject json = Json.createObjectBuilder()
                                        .add("message", "user %s not found".formatted(userId))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(json.toString());
                }

                Optional<Preference> optPref = infoRtrvSvc.getUserPreference(userId);
                Preference pref = new Preference();

                if (optPref.isPresent()) {
                        pref = optPref.get();
                }
                Optional<List<Friendship>> optPotentialMatches = friendshipSvc.getAllFriendshipReq(userId, "pending");
                Optional<List<CompleteProfile>> optProfileMatches = matchSvc.getMatchedProfiles(userId, pref);
                Optional<List<MatchingScore>> optInterestMatches = matchSvc.getMatchedInterestsAndSubs(userId);
                Optional<List<String>> optCommonIds = matchSvc.getMatchedUsersByProfilesAndInterests(userId, pref);

                List<JsonObject> profiles = new ArrayList<>();

                if (optCommonIds.isPresent()) {
                        List<String> commonIds = optCommonIds.get();
                        List<MatchingScore> matches = optInterestMatches.get();

                        System.out.printf(">>>>>>> commonids: %s\n", commonIds.toString());
                        System.out.printf(">>>>>>> matches: %s\n", matches.toString());

                        profiles = commonIds.stream()
                                        .map(id -> infoRtrvSvc.getCompleteProfile(id))
                                        .filter(Optional::isPresent)
                                        .map(Optional::get)
                                        .map(profile -> {

                                                Optional<MatchingScore> matchingScore = matches.stream()
                                                                .filter(score -> score.getUserId()
                                                                                .equals(profile.getUserId()))
                                                                .findFirst();

                                                if (matchingScore.isPresent()) {
                                                        Double interestScore = matchingScore.get().getInterestScore();

                                                        Double subInterestScore = matchingScore.get()
                                                                        .getSubInterestScore();

                                                        profile.setInterestScore(interestScore);
                                                        profile.setSubInterestScore(subInterestScore);
                                                }

                                                return CompleteProfile.toJsonForProfileInterestMatch(profile);

                                        })
                                        .collect(Collectors.toList());

                }

                JsonObjectBuilder payload = Json.createObjectBuilder()
                                .add("userId", userId);

                if (optPotentialMatches.isPresent()) {

                        payload.add("potentialMatches", Friendship.toJsonArray(optPotentialMatches.get()));
                } else {
                        payload.add("potentialMatches", "");
                }

                if (profiles.size() > 0) {
                        payload.add("profileInterestMatches", CompleteProfile.toJsonArray(profiles));
                } else {
                        payload.add("profileInterestMatches", "");
                }

                if (optProfileMatches.isPresent()) {
                        payload.add("profileMatches", matchSvc.toJsonArray(optProfileMatches.get()));
                } else {
                        payload.add("profileMatches", "");
                }

                if (optInterestMatches.isPresent()) {
                        payload.add("interestMatches", MatchingScore.toJsonArr(optInterestMatches.get()));
                } else {
                        payload.add("interestMatches", "");
                }

                JsonObject result = payload.build();

                return ResponseEntity
                                .status(200)
                                .body(result.toString());
        }
}
