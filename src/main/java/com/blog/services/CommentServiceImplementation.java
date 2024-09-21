package com.blog.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Dto.CommentDto;
import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exception.ResourceNotfoundException;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;

@Service
public class CommentServiceImplementation implements CommentService{

	@Autowired
	 private PostRepo postrepo;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private CommentRepo commentrepo;
	
	@Autowired
	private ModelMapper mm;
	
	@Override
	public CommentDto create(CommentDto commentdto, int postid,int userid) {
		
	      Post post = postrepo.findById(postid).orElseThrow( () -> new ResourceNotfoundException("post","id",postid));
	      User user = userrepo.findById(userid).orElseThrow( () -> new ResourceNotfoundException("user","id",userid));
	      Comment comment = mm.map(commentdto, Comment.class);
	      comment.setPost(post);
	      post.setComment(post.getComment());
	      
	      comment.setUser(user);
	      user.setComment(user.getComment());
	      return mm.map(commentrepo.save(comment), CommentDto.class);
		}

	@Override
	public void delete(int commentid) {
		Comment orElseThrow = commentrepo.findById(commentid).orElseThrow(()->new ResourceNotfoundException("comment","id",commentid));
		commentrepo.delete(orElseThrow);
	}

}

