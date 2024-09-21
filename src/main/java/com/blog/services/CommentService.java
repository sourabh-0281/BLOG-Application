package com.blog.services;

import com.blog.Dto.CommentDto;

public interface CommentService {

	//get
	CommentDto create(CommentDto comment,int id,int userid);
	
	//delete
	void delete(int commentid);
}
