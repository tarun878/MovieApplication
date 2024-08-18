package com.tarun.movieReviewApp.services;

import com.tarun.movieReviewApp.entities.User;
import com.tarun.movieReviewApp.payloads.UserDto;
import com.tarun.movieReviewApp.payloads.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserDto userDto);
    UserResponseDto loginUser(UserDto userDto);
    User getUserByUsername(String username);
    }

