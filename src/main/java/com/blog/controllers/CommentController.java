package com.blog.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Dto.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private CommentService commentservice;
	
	@PostMapping("/post/{postid}/user/{userid}")
	public ResponseEntity<?> create(@RequestBody CommentDto cdto,@PathVariable int postid,@PathVariable int userid){
		
		CommentDto commentDto = commentservice.create(cdto, postid,userid);
		
		return new ResponseEntity(commentDto,HttpStatus.OK);
	}
	
	@GetMapping("/delete/{cid}")
	public ResponseEntity<?> delete(@PathVariable int cid){
		
		commentservice.delete(cid);
        Map m=new HashMap<>();
        
        m.put("message", "Deleted succesfully");
        
		return ResponseEntity.ok(m);
 }
	
	
}