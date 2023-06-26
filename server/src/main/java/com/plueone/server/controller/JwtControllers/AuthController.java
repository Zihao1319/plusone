// package com.plueone.server.controller.JwtControllers;

// import java.util.LinkedList;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// import com.plueone.server.jwtPayload.request.LoginRequest;
// import com.plueone.server.jwtPayload.request.SignupRequest;
// import com.plueone.server.jwtPayload.response.JwtResponse;
// import com.plueone.server.jwtPayload.response.MessageResponse;
// import com.plueone.server.models.JwtAuth.Role;
// import com.plueone.server.models.JwtAuth.User;
// import com.plueone.server.repo.JwtRepo.RoleRepository;
// import com.plueone.server.repo.JwtRepo.UserRepository;
// import com.plueone.server.security.jwt.JwtUtils;
// import com.plueone.server.security.services.UserDetailsImpl;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import jakarta.json.Json;
// import jakarta.json.JsonObject;

// @RestController
// @RequestMapping(path = "/api")
// public class AuthController {

//     @Autowired
//     AuthenticationManager authenticationManager;

//     @Autowired
//     UserRepository userRepository;

//     @Autowired
//     RoleRepository roleRepository;

//     @Autowired
//     PasswordEncoder encoder;

//     @Autowired
//     JwtUtils jwtUtils;

//     @PostMapping("/signin")
//     public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

//         Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword()));

//         System.out.printf(">>>>>>>>signupreq: %s\n", authentication);

//         SecurityContextHolder.getContext().setAuthentication(authentication);
//         String jwt = jwtUtils.generateJwtToken(authentication);

//         UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//         List<String> roles = userDetails.getAuthorities().stream()
//                 .map(item -> item.getAuthority())
//                 .collect(Collectors.toList());

//         return ResponseEntity.ok(new JwtResponse(jwt,
//                 userDetails.getUserId(),
//                 userDetails.getUsername(),
//                 userDetails.getEmail(),
//                 roles));
//     }

//     @PostMapping(path = "/signup")
//     public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

//         System.out.printf(">>>>>>>>signupreq: %s\n", signUpRequest.toString());

//         if (userRepository.existsByName(signUpRequest.getName())) {
//             return ResponseEntity
//                     .badRequest()
//                     .body(new MessageResponse("Error: Username is already taken!"));
//         }

//         if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//             return ResponseEntity
//                     .badRequest()
//                     .body(new MessageResponse("Error: Email is already in use!"));
//         }

//         // Create new user's account
//         User user = new User(signUpRequest.getName(),
//                 signUpRequest.getEmail(),
//                 encoder.encode(signUpRequest.getPassword()));

//         List<Role> strRoles = signUpRequest.getRoles();
//         List<Role> roles = new LinkedList<>();

//         if (strRoles == null) {

//             Optional<Role> optRole = roleRepository.findByName(signUpRequest.getName());

//             if (optRole.isEmpty()) {
//                 throw new RuntimeException("Error: Role is not found.");
//             }

//             Role userRole = optRole.get();
//             roles.add(userRole);

//         } else {
//             strRoles.forEach(role -> {
//                 String roleName = role.getRoleName();
//                 switch (roleName) {

//                     case "admin":

//                         Optional<Role> optAdminRole = roleRepository.findByName(signUpRequest.getName());

//                         if (optAdminRole.isEmpty()) {
//                             throw new RuntimeException("Error: Role is not found.");
//                         }

//                         Role adminRole = optAdminRole.get();
//                         roles.add(adminRole);
//                         break;

//                     case "mod":
//                         Optional<Role> optModRole = roleRepository.findByName(signUpRequest.getName());

//                         if (optModRole.isEmpty()) {
//                             throw new RuntimeException("Error: Role is not found.");
//                         }

//                         Role modRole = optModRole.get();
//                         roles.add(modRole);
//                         break;

//                     default:
//                         Optional<Role> optUserRole = roleRepository.findByName(signUpRequest.getName());

//                         if (optUserRole.isEmpty()) {
//                             throw new RuntimeException("Error: Role is not found.");
//                         }

//                         Role userRole = optUserRole.get();
//                         roles.add(userRole);
//                 }
//             });
//         }

//         user.setRoles(roles);

//         String userId = userRepository.createUser(user);
//         roleRepository.insertUserRoles(user, userId);

//         JsonObject json = Json.createObjectBuilder()
//                 .add("userId", userId)
//                 .build();

//         return ResponseEntity
//                 .status(200)
//                 .body(json.toString());

//     }

// }