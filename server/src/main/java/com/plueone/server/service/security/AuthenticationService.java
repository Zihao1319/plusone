package com.plueone.server.service.security;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.plueone.server.models.JwtAuth.LoginResponseDTO;
import com.plueone.server.models.JwtAuth.Role;
import com.plueone.server.models.JwtAuth.User;
import com.plueone.server.repo.JwtRepo.RoleRepository;
import com.plueone.server.repo.JwtRepo.UserRepository;

import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenSvc;

    public ResponseEntity<String> loginUser(String username, String password) {

        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenSvc.generateJwt(auth);

            JsonObject json = responseBuilder
                    .add("name", userRepo.findByName(username).get().getName())
                    .add("userId", userRepo.findByName(username).get().getUserId())
                    .add("token", token)
                    .build();

            return ResponseEntity
                    .status(200)
                    .body(json.toString());

        } catch (BadCredentialsException e) {
            responseBuilder.add("message", "username or password is wrong");
        }

        catch (AuthenticationException e) {
            responseBuilder.add("message", "login failed");
        }

        JsonObject res = responseBuilder.build();

        return ResponseEntity
                .status(400)
                .body(res.toString());

    }

    public ResponseEntity<String> registerUser(String name, String email, String password) {
        String encodedPassword = encoder.encode(password);
        Role userRole = roleRepo.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        String userId = UUID.randomUUID().toString().substring(0, 6);
        userRepo.save(new User(userId, name, email, encodedPassword, authorities));

        return loginUser(name, password);

        // return userId;

    }
}
