package com.tarun.movieReviewApp.controller;

import com.tarun.movieReviewApp.payloads.UserDto;
import com.tarun.movieReviewApp.payloads.UserResponseDto;
import com.tarun.movieReviewApp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserDto userDTO) {
        UserResponseDto response = userService.registerUser(userDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserDto userDTO) {
        UserResponseDto response = userService.loginUser(userDTO);
        return ResponseEntity.ok(response);
    }
}
