package com.plueone.server.controller.JwtControllers;

import com.plueone.server.models.JwtAuth.LoginResponseDTO;
import com.plueone.server.models.JwtAuth.RegistrationDTO;
import com.plueone.server.models.JwtAuth.User;
import com.plueone.server.repo.JwtRepo.UserRepository;
import com.plueone.server.service.security.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationService authSvc;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationDTO body) {

        if (userRepo.findByName(body.getName()).isPresent()) {
            JsonObject json = Json.createObjectBuilder()
                    .add("message", "name is already taken")
                    .build();

            return ResponseEntity
                    .status(400)
                    .body(json.toString());

        }

        if (userRepo.existsByEmail(body.getEmail())) {

            JsonObject json = Json.createObjectBuilder()
                    .add("message", "email is already taken")
                    .build();

            return ResponseEntity
                    .status(400)
                    .body(json.toString());

        }

        return authSvc.registerUser(body.getName(), body.getEmail(), body.getPassword());

    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody RegistrationDTO body) {
        return authSvc.loginUser(body.getName(), body.getPassword());
    }

}
