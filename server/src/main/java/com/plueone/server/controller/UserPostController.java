package com.plueone.server.controller;

import java.util.List;

import com.plueone.server.exception.ResourceNotFoundException;
import com.plueone.server.models.Answer;
import com.plueone.server.models.Interest;
import com.plueone.server.models.InterestWrapper;
import com.plueone.server.models.Language;
import com.plueone.server.models.Personality;
import com.plueone.server.models.Preference;
import com.plueone.server.models.Profile;
import com.plueone.server.models.Subinterest;
import com.plueone.server.models.JwtAuth.User;
import com.plueone.server.service.InfoInsertionService;
import com.plueone.server.service.InfoRetrievalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api")
public class UserPostController {

        @Autowired
        private InfoInsertionService infoInstSvc;

        @Autowired
        private InfoRetrievalService infoRtrvSvc;

        // @PostMapping(path = "/register")
        // public ResponseEntity<String> createNewUser(@RequestBody User user) {

        //         String name = user.getName();
        //         String phoneNum = user.getPhoneNum();
        //         String email = user.getEmail();

        //         int count = infoRtrvSvc.checkIfUserEmailExists(email);

        //         if (count > 0) {
        //                 JsonObject message = Json.createObjectBuilder()
        //                                 .add("message", "User already exists")
        //                                 .build();

        //                 return ResponseEntity
        //                                 .status(409)
        //                                 .body(message.toString());

        //         } else {
        //                 String userId = infoInstSvc.createOrUpdateUser(name, phoneNum, email);

        //                 JsonObject message = Json.createObjectBuilder()
        //                                 .add("userId", userId)
        //                                 .build();

        //                 return ResponseEntity
        //                                 .status(201)
        //                                 .body(message.toString());
        //         }

        // }

        // create or update user profile
        @PostMapping(path = "/profile/{userId}")
        public ResponseEntity<String> createOrUpdateUserProfile(@PathVariable String userId, @RequestBody Profile profile) {

                int userExists = infoRtrvSvc.checkIfUserIdExists(userId);

                if (userExists == 0) {

                        JsonObject json = Json.createObjectBuilder()
                                        .add("message", "user %s not found".formatted(userId))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(json.toString());
                }

                int iUpdate = infoInstSvc.createOrUpdateProfile(userId, profile);

                if (iUpdate > 0) {
                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "Profile created/updated successfully")
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(message.toString());

                } else {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "Error in creating/updating profile")
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(message.toString());
                }

        }

        @PostMapping(path = "/language/{userId}")
        public ResponseEntity<String> insertUserLanguages(@PathVariable String userId,
                        @RequestBody List<Language> languages) {

                int[] iUpdates = infoInstSvc.insertUserLanguages(userId, languages);

                if (iUpdates.length > 0) {
                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "%d languages added".formatted(iUpdates.length))
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(message.toString());

                } else {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "Error in adding user's langugages")
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(message.toString());
                }

        }

        @PostMapping(path = "/personality/{userId}")
        public ResponseEntity<String> insertUserPersonality(@PathVariable String userId,
                        @RequestBody List<Personality> personalities) {

                int[] iUpdates = infoInstSvc.insertUserPersonality(userId, personalities);

                if (iUpdates.length > 0) {
                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "%d personalities added".formatted(iUpdates.length))
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(message.toString());

                } else {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "Error in adding user's personalities")
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(message.toString());
                }

        }

        // create or update user preference
        @PostMapping(path = "/preference/{userId}")
        public ResponseEntity<String> createUserPreference(@PathVariable String userId,
                        @RequestBody Preference preference) {

                int iUpdate = infoInstSvc.createOrUpdatePreference(userId, preference);

                if (iUpdate > 0) {
                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "Preference created/updated successfully")
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(message.toString());

                } else {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "Error in creating/updating Preference")
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(message.toString());
                }

        }

        // create or update user prompts
        @PostMapping(path = "/prompt/{userId}")
        public ResponseEntity<String> insertUserAnswers(@PathVariable String userId,
                        @RequestBody List<Answer> answers) {

                int[] iUpdates = infoInstSvc.insertUserAnswers(userId, answers);

                if (iUpdates.length > 0) {
                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "User's answers created successfully")
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(message.toString());

                } else {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "Error in creating answers")
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(message.toString());
                }
        }

        // upload images
        @PostMapping(path = "/image/{userId}")
        public ResponseEntity<String> insertUserImages(@PathVariable String userId, @RequestPart MultipartFile file) {

                try {

                        String imageUrl = infoInstSvc.insertImages(file, userId);

                        JsonObject message = Json.createObjectBuilder()
                                        .add("imageUrl", imageUrl)
                                        .build();

                        return ResponseEntity
                                        .status(201)
                                        .body(message.toString());

                } catch (ResourceNotFoundException ex) {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", ex.getMessage())
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(message.toString());
                }

        }


        @PostMapping(path = "/add/interests/{userId}")
        public ResponseEntity<String> insertUserInterestss(@PathVariable String userId,
                        @RequestBody List<Interest> interests) {


                int[] iInterestUpdates = infoInstSvc.insertUserInterest(userId, interests);

                if (iInterestUpdates.length > 0) {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("interests added", iInterestUpdates.length)
                                        .build();

                        return ResponseEntity
                                        .status(201)
                                        .body(message.toString());

                } else {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "error in adding interests")
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(message.toString());
                }

        }


        @PostMapping(path = "/add/subinterests/{userId}")
        public ResponseEntity<String> insertUserInterestsAndSubInterests(@PathVariable String userId,
                        @RequestBody List<Subinterest> subInterests) {

                int[] iSubInterestUpdates = infoInstSvc.insertUserSubInterest(userId, subInterests);

                if (iSubInterestUpdates.length > 0) {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("subInterests added", iSubInterestUpdates.length)
                                        .build();

                        return ResponseEntity
                                        .status(201)
                                        .body(message.toString());

                } else {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "error in adding subinterests")
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(message.toString());
                }

        }

        @PutMapping(path = "/edit/answer/{id}")
        public ResponseEntity<String> editUserAnswer(@PathVariable String id, @RequestBody Answer answer) {

                int iUpdate = infoInstSvc.editUserAnswer(answer);

                if (iUpdate > 0) {
                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "AnswerID: %d edited".formatted(answer.getId()))
                                        .build();

                        return ResponseEntity
                                        .status(200)
                                        .body(message.toString());

                } else {

                        JsonObject message = Json.createObjectBuilder()
                                        .add("message", "Error in editing answerId: %d".formatted(answer.getId()))
                                        .build();

                        return ResponseEntity
                                        .status(404)
                                        .body(message.toString());
                }
        }

}
