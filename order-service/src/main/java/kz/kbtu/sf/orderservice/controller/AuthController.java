package kz.kbtu.sf.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import kz.kbtu.sf.orderservice.jwt.*;
import kz.kbtu.sf.orderservice.entity.*;
import kz.kbtu.sf.orderservice.repository.*;
import kz.kbtu.sf.orderservice.entity.request.*;
import kz.kbtu.sf.orderservice.entity.response.AuthResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Register a new user
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user with email and password")
    public ResponseEntity<?> register(@RequestBody AuthRegisterRequest registerRequest) {
        Optional<User> user = userRepository.findByEmail(registerRequest.getEmail());

        if (user.isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        Role defaultRole = roleRepository.findByName("ROLE_ADMIN")
               .orElseThrow(() -> new IllegalArgumentException("Default role not found."));

        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.addRole(defaultRole);


        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully.");
    }

    /**
     * Authenticate a user and return a JWT token
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate a user and return a JWT token")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));

            String roles = String.join(",", user.getRoles().stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toList()));

            String token = jwtTokenUtil.generateAccessToken(user.getId(), user.getEmail(), Collections.singletonList(roles));
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    /**
     * Extract the user ID from a JWT token
     */
    @GetMapping("/user-id")
    @Operation(summary = "Get user ID from token", description = "Extract the user ID from a JWT token")
    public ResponseEntity<UUID> getUserIdFromToken(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = jwtTokenUtil.extractUserId(jwt);
        return ResponseEntity.ok(userId);
    }
}