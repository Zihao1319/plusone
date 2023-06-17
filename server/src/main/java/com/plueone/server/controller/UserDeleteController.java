package com.plueone.server.controller;

import com.plueone.server.service.InfoDeletionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api")
public class UserDeleteController {

    @Autowired
    private InfoDeletionService infoDelSvc;

    // delete everything linked with userid in sql
    @DeleteMapping(path = "/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {

        int iUpdate = infoDelSvc.deleteUser(userId);

        if (iUpdate > 0) {
            JsonObject message = Json.createObjectBuilder()
                    .add("message", "user %s deleted".formatted(userId))
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(message.toString());

        } else {

            JsonObject message = Json.createObjectBuilder()
                    .add("message", "user %s doesn't exist".formatted(userId))
                    .build();

            return ResponseEntity
                    .status(404)
                    .body(message.toString());
        }
    }

    @DeleteMapping(path = "/language/{userId}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable String userId, @RequestParam Integer id) {

        int iUpdate = infoDelSvc.deleteLanguage(userId, id);

        if (iUpdate > 0) {
            JsonObject message = Json.createObjectBuilder()
                    .add("message", "languageId %s deleted".formatted(id))
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(message.toString());

        } else {

            JsonObject message = Json.createObjectBuilder()
                    .add("message", "languageId %s doesn't exist".formatted(id))
                    .build();

            return ResponseEntity
                    .status(404)
                    .body(message.toString());
        }
    }

    @DeleteMapping(path = "/interest/{userId}")
    public ResponseEntity<String> deleteUserInterest(@PathVariable String userId, @RequestParam Integer id) {

        int iUpdate = infoDelSvc.deleteInterest(userId, id);

        if (iUpdate > 0) {
            JsonObject message = Json.createObjectBuilder()
                    .add("message", "interestId %d deleted".formatted(id))
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(message.toString());

        } else {

            JsonObject message = Json.createObjectBuilder()
                    .add("message", "interestId %d doesn't exist".formatted(id))
                    .build();

            return ResponseEntity
                    .status(404)
                    .body(message.toString());
        }
    }

    @DeleteMapping(path = "/subinterest/{userId}")
    public ResponseEntity<String> deleteUserSubInterest(@PathVariable String userId, @RequestParam Integer id) {

        int iUpdate = infoDelSvc.deleteSubInterest(userId, id);

        if (iUpdate > 0) {
            JsonObject message = Json.createObjectBuilder()
                    .add("message", "sub-interestId %d deleted".formatted(id))
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(message.toString());

        } else {

            JsonObject message = Json.createObjectBuilder()
                    .add("message", "sub-interestId %d doesn't exist".formatted(id))
                    .build();

            return ResponseEntity
                    .status(404)
                    .body(message.toString());
        }
    }

    @DeleteMapping(path = "/personality/{userId}")
    public ResponseEntity<String> deleteUserPersonality(@PathVariable String userId, @RequestParam Integer id) {

        int iUpdate = infoDelSvc.deletePersonality(userId, id);

        if (iUpdate > 0) {
            JsonObject message = Json.createObjectBuilder()
                    .add("message", "personalityId %d deleted".formatted(id))
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(message.toString());

        } else {

            JsonObject message = Json.createObjectBuilder()
                    .add("message", "personalityId %d doesn't exist".formatted(id))
                    .build();

            return ResponseEntity
                    .status(404)
                    .body(message.toString());
        }
    }

    @DeleteMapping(path = "/prompt/{userId}")
    public ResponseEntity<String> deleteUserAnswer(@PathVariable String userId, @RequestParam Integer id) {

        int iUpdate = infoDelSvc.deleteAnswer(userId, id);

        if (iUpdate > 0) {
            JsonObject message = Json.createObjectBuilder()
                    .add("message", "answer to promptId %d deleted".formatted(id))
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(message.toString());

        } else {

            JsonObject message = Json.createObjectBuilder()
                    .add("message", "answer to promptId %d doesn't exist".formatted(id))
                    .build();

            return ResponseEntity
                    .status(404)
                    .body(message.toString());
        }
    }

    @DeleteMapping(path = "/image/{imageId}")
    public ResponseEntity<String> deleteUserImage(@PathVariable Integer imageId, @RequestParam String url) {

        int iUpdate = infoDelSvc.deleteImage(imageId, url);

        if (iUpdate > 0) {
            JsonObject message = Json.createObjectBuilder()
                    .add("message", "imageId %d deleted".formatted(imageId))
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(message.toString());

        } else {

            JsonObject message = Json.createObjectBuilder()
                    .add("message", "imageId %d doesn't exist".formatted(imageId))
                    .build();

            return ResponseEntity
                    .status(404)
                    .body(message.toString());
        }
    }

}
