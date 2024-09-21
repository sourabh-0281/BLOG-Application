package com.blog.Dto;

import java.util.HashSet;
import java.util.Set;

import com.blog.entities.Comment;
import com.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//to avoid direct  use of Entity class we have created this class
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private int id;
	@NotBlank
	@Size(min = 4,message = "name must be more than 4 characters and should not be blank")
	private String name;
	
	@Email(message = "email is not valid")
	@NotBlank
	private String email;
	
	@NotBlank(message = "password must be more than 3 characters and less than 10")
	@Size(min = 3, max=10)
	private String password;
	
	@NotBlank()
	private String about;
    
	private Set<CommentDto> comment=new HashSet<>();
	private Set<Role>role=new HashSet<>();

}
