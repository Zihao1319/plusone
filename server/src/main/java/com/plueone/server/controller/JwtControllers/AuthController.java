package com.plueone.server.controller.JwtControllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.plueone.server.jwtPayload.request.LoginRequest;
import com.plueone.server.jwtPayload.request.SignupRequest;
import com.plueone.server.jwtPayload.response.JwtResponse;
import com.plueone.server.jwtPayload.response.MessageResponse;
import com.plueone.server.models.JwtAuth.Role;
import com.plueone.server.models.JwtAuth.User;
import com.plueone.server.repo.JwtRepo.RoleRepository;
import com.plueone.server.repo.JwtRepo.UserRepository;
import com.plueone.server.security.jwt.JwtUtils;
import com.plueone.server.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;


