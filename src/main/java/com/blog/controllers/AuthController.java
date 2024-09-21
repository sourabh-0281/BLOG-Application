package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Dto.UserDto;
import com.blog.JWT.JWTAuthRequest;
import com.blog.JWT.JWTAuthResponse;
import com.blog.JWT.JWTTokenHelper;
import com.blog.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JWTTokenHelper jwthelper;
	
	@Autowired
	private UserDetailsService userdetailsservice;
	
	@Autowired
	private UserService us;
	
	@Autowired
	private AuthenticationManager authenticationmanager;
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createtoken(@RequestBody JWTAuthRequest req){
	
		authenticate(req.getUsername(),req.getPassword());
		
		UserDetails userByUsername = userdetailsservice.loadUserByUsername(req.getUsername());
		
		String token = jwthelper.generateToken(userByUsername.getUsername());
		
		JWTAuthResponse res=new JWTAuthResponse() ;
		res.setToken(token);
		
		return ResponseEntity.ok(res);
	}

	private void authenticate(String username, String password) {	
		UsernamePasswordAuthenticationToken authtoken =new UsernamePasswordAuthenticationToken(username, password);
		 try {
			 authenticationmanager.authenticate(authtoken);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid password");
		}
	}
	
	//register new user
	@PostMapping("/registeruser")
	public ResponseEntity<UserDto> register(@RequestBody UserDto userdto){
		UserDto registeruser = us.registeruser(userdto);
		return ResponseEntity.ok(registeruser);
	} 
}
