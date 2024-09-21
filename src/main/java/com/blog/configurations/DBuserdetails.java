package com.blog.configurations;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blog.entities.Role;
import com.blog.entities.User;


public class DBuserdetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User u;
	public DBuserdetails(User u) {
	 this.u=u;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		 Set<Role> role = u.getRole();
    	List<SimpleGrantedAuthority> collect = role.stream().map(r-> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
		return collect;
	}

	@Override
	public String getPassword() {
		
		return u.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return u.getEmail();
	}

}
