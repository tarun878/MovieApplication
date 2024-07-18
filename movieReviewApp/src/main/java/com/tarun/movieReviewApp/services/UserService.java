package com.tarun.movieReviewApp.services;

import com.tarun.movieReviewApp.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Long userId);
    UserDto getUserById(Long userId);
    List<UserDto>getAllUsers();
    void deleteUser(Long userId);
}

