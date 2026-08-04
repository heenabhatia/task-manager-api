package com.heena.taskmanager.service;

import com.heena.taskmanager.dto.LoginRequest;
import com.heena.taskmanager.dto.LoginResponse;
import com.heena.taskmanager.dto.UserRegistrationRequest;
import com.heena.taskmanager.dto.UserRegistrationResponse;
import com.heena.taskmanager.exception.UsernameAlreadyExistsException;
import com.heena.taskmanager.model.User;
import com.heena.taskmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());

        if (existingUser.isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return new UserRegistrationResponse(
                savedUser.getId(), savedUser.getUsername());
    }

    public LoginResponse loginUser(LoginRequest request) {
        User existingUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (passwordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
            String generatedToken = jwtService.generateToken(existingUser.getUsername());
            return new LoginResponse(generatedToken);
        }
        throw new RuntimeException("Invalid username or password");
    }
}
