package com.tarun.movieReviewApp.services.impl;

import com.tarun.movieReviewApp.entities.User;
import com.tarun.movieReviewApp.payloads.UserDto;
import com.tarun.movieReviewApp.payloads.UserResponseDto;
import com.tarun.movieReviewApp.repository.UserRepo;
import com.tarun.movieReviewApp.services.UserService;
import com.tarun.movieReviewApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserResponseDto registerUser(UserDto userDto) {
        if (userRepo.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already taken");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        userRepo.save(user);

        String token = jwtUtil.generatedToken(user.getUsername());
        return new UserResponseDto(user.getUsername(), user.getEmail(), token);
    }

    @Override
    public UserResponseDto loginUser(UserDto userDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtUtil.generatedToken(userDto.getUsername());

                User user = userRepo.findByUsername(userDto.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                return new UserResponseDto(user.getUsername(), user.getEmail(), token);
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }

        throw new RuntimeException("Login failed");
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}