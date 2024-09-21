package com.blog.services;

import java.util.List;

import com.blog.Dto.CommentDto;
import com.blog.Dto.PaginationResponse;
import com.blog.Dto.PostDto;
import com.blog.entities.Post;

public interface PostService {

	//create
	PostDto create(PostDto postdto,int userid,int categoryid);
	
	//update
	PostDto update(PostDto postdto,int id);
	
	//delete
	void deletepost(int id);
	
	//getsingle post
	PostDto getsingle(int id);
	
	//getallpost
	PaginationResponse  getallpost(int pagenumber,int pagesize,String sortby,String sortdir);
	
	//get all post by category
	PaginationResponse getPostByCategory(int cid,int pagenumber,int pagesize,String sortby,String sortdir);
	
	//get pots by user
	List<PostDto> getPostByUser(int uid);
	
	//get by comment
	List<CommentDto>  getCommentbyPost(int cid);
	
	//Search
	List<PostDto> searchPosts(String keyword);
}
