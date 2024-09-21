package com.blog.controllers;

import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Dto.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userservice;
	
	//post
	@PostMapping("")
	public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userdto){
		
		UserDto createuser = userservice.createuser(userdto);
		return new ResponseEntity(createuser,HttpStatus.CREATED);
	}
	
	//Update
	@PutMapping("/{userid}")
	public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto user,@PathVariable int userid){
		UserDto updateuser = userservice.updateuser(user, userid);
		return ResponseEntity.ok(updateuser);
	}
	
	///delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id){
		userservice.deleteUser(id);
		return new ResponseEntity<Map<String,String>> (Map.of("Message","user deleted succesfully"),HttpStatus.OK);
	}
	
	//get single user
	@GetMapping("/{uid}")
	public ResponseEntity<UserDto> getsingleuser(@PathVariable int uid ) {
		UserDto userid = userservice.getuserById(uid);
		return new ResponseEntity<UserDto>(userid,HttpStatus.OK);
	}
	
	//get all user
	@GetMapping("")
	public ResponseEntity<List<UserDto> > getalluser() {
		List<UserDto> alluser = userservice.getAlluser();
		return new ResponseEntity(alluser,HttpStatus.OK);
	}
}
