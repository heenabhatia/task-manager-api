package com.heena.taskmanager.controller;

import com.heena.taskmanager.dto.LoginRequest;
import com.heena.taskmanager.dto.LoginResponse;
import com.heena.taskmanager.dto.UserRegistrationRequest;
import com.heena.taskmanager.dto.UserRegistrationResponse;
import com.heena.taskmanager.service.JwtService;
import com.heena.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {

        UserRegistrationResponse response = userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = userService.loginUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/username")
    public String extractUsername(@RequestParam String token) {
        return jwtService.extractUsername(token);
    }

}
