package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.configurations.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	@Bean
	public ModelMapper mm() {
		return new ModelMapper();
	}

	
 @Autowired
 PasswordEncoder pe;
 
 @Autowired
 RoleRepo rr;
	@Override
	public void run(String... args) throws Exception {
       try {
    	   Role role=new Role();
    	   role.setId(AppConstants.ADMIN_USER);
    	   role.setName("ROLE_ADMIN");
    	   
    	   Role role1=new Role();
    	   role1.setId(AppConstants.NORMAL_USER);
    	   role1.setName("ROLE_NORMAL");
    	   
    	   List<Role> of = List.of(role,role1);
    	   rr.saveAll(of);
       }catch (Exception e) {
		
	}			
	}

}
