package com.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.Dto.UserDto;

public interface UserService {

	UserDto registeruser(UserDto user);
	UserDto createuser(UserDto user);
	UserDto updateuser(UserDto user,int id);
	UserDto getuserById(int id);
    List<UserDto> getAlluser();
    void deleteUser(int id);
}

