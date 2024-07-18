package com.tarun.movieReviewApp.controller;

import com.tarun.movieReviewApp.payloads.ApiResponse;
import com.tarun.movieReviewApp.payloads.UserDto;
import com.tarun.movieReviewApp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    //post -create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto= this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

    }
    //put - update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId")  Long uid){
    UserDto updatedUser = this.userService.updateUser(userDto, uid);
    return ResponseEntity.ok(updatedUser);
    }
    //Delete - delete user
    @DeleteMapping("/{userId}" )
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
    }
    //Get - get All users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }
    //Get - get single user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }


}
