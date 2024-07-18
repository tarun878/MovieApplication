package com.tarun.movieReviewApp.services.impl;

import com.tarun.movieReviewApp.entities.User;
import com.tarun.movieReviewApp.exception.ResourceNotFoundException;
import com.tarun.movieReviewApp.payloads.UserDto;
import com.tarun.movieReviewApp.repository.UserRepo;
import com.tarun.movieReviewApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","Id",userId));


        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;

    }


    @Override
    public void deleteUser(Long userId) {
        User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","Id",userId));
        this.userRepo.delete(user);

    }
    public User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);

        return user;
    }
    public UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
