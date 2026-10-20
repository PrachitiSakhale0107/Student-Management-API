package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.LoginRequest;
import com.example.studentmanagement.dto.RegisterRequest;
import com.example.studentmanagement.entity.AppUser;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder encoder,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        this.users = users; this.encoder = encoder;
        this.authenticationManager = authenticationManager; this.jwtService = jwtService;
    }

    public Map<String, String> register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        if (users.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }
        users.save(new AppUser(email, encoder.encode(request.password())));
        return Map.of("message", "User registered successfully", "email", email);
    }

    public Map<String, String> login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, request.password()));
        AppUser user = (AppUser) authentication.getPrincipal();
        return Map.of("message", "Login successful", "token", jwtService.generateToken(user),
            "tokenType", "Bearer", "email", user.getEmail());
    }
}
