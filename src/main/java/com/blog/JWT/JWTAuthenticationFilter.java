package com.blog.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsService userdetailservice;
	
	@Autowired
	private JWTTokenHelper jwttokenhelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//               1.get jwt token from request header
		
		       String header = request.getHeader("Authorization");
		       
		       String username=null;
		       String token=null;
		       
		       if(header!=null && header.startsWith("Bearer")) {
		    	   
		    	   token = header.substring(7);
		          try {    	 
//				 2. Get username from token		        	  
		    	  username = jwttokenhelper.extractUsername(token);
		    	  
		           }
		          catch(IllegalArgumentException e) {
		        	  System.out.println("Unable to get JWTToken");
		           }
		          catch (ExpiredJwtException e) {
	                 System.out.println("JWT token has Expired");
				  }
		          catch (MalformedJwtException e) {
					System.out.println("Invalis jwt");
				  }
		    	 
		       }else{
		    	   System.out.println("Jwt token does not begin with  Bearer");
		       }
		       
		       
//              3.Validate
		       if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null	) {
//              4.load user-name		    	   
		       		    	   UserDetails userByUsername = userdetailservice.loadUserByUsername(username);
		       		    	  if( jwttokenhelper.validateToken(token, username)) {
		       		    		UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userByUsername, null,userByUsername.getAuthorities());
		       		    		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
								SecurityContextHolder.getContext().setAuthentication(authentication);
		       		    		  
		       		    	  }else {
		       		    		  System.out.println("Invalid Jwt Token");
		       		    	  }
		       }else {
		    	   System.out.println("username is null or context is not null");
		       }
     
		       filterChain.doFilter(request, response);
	}

}
