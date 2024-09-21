package com.blog.Dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.entities.Category;
import com.blog.entities.Comment;
import com.blog.entities.User;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDto {
	
	private int id;
	private String title;
	private String content;
	private String imgname;
	private Date addeddate;
	private CategoryDto category;
	private UserDto user;
    private Set<CommentDto>comment=new HashSet<>(); 

}
