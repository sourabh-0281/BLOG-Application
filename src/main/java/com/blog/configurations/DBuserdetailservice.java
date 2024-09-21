package com.blog.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exception.ResourceNotfoundException;
import com.blog.repositories.UserRepo;

public class DBuserdetailservice implements UserDetailsService {

	@Autowired
	private UserRepo userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User userByEmail = userrepo.findByEmail(username);

		if(userByEmail==null) {
			throw new ResourceNotfoundException("user with "+username+" does not exists");
		}
		DBuserdetails du=new DBuserdetails(userByEmail);
		return du;
	}

}
