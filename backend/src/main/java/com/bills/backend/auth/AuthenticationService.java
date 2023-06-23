package com.bills.backend.auth;

import com.bills.backend.model.Role;
import com.bills.backend.model.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bills.backend.config.JwtService;
import com.bills.backend.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserServiceImpl userServiceImpl;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = Users.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder().encode(request.getPassword()))
            .role(Role.USER)
            .build();
        userServiceImpl.saveUser(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        var user = userServiceImpl.getUserByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }
}
