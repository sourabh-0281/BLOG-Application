package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.Dto.CommentDto;
import com.blog.Dto.PaginationResponse;
import com.blog.Dto.PostDto;
import com.blog.configurations.AppConstants;
import com.blog.entities.Post;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class PostControler {

	@Autowired
	private PostService postservice;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.image}")
	private String path;
	
	//CREATE
	@PostMapping("/user/{userid}/category/{categoryid}/comment/{commentid}/post")
	public ResponseEntity<PostDto> create(@RequestBody  PostDto postdto, @PathVariable int userid,@PathVariable int categoryid,@PathVariable int commentid){	
			PostDto postdto1 = postservice.create(postdto, userid, categoryid);
			return new ResponseEntity<PostDto>(postdto1,HttpStatus.CREATED);
	}
	
	
	//GET POSTS BY USER
	@GetMapping("/user/{userid}/post")
	public ResponseEntity< List<PostDto>> getpostbyuser(@PathVariable int userid) {
		List<PostDto> postByUser = postservice.getPostByUser(userid);
		return new ResponseEntity(postByUser,HttpStatus.OK);
	}
	
	//GET POSTS BY CATEGORY
		@GetMapping("/category/{categoryid}/post")
		public ResponseEntity<PaginationResponse> getpostbycategory(
																			@PathVariable int categoryid,
																			@RequestParam(value = "pagenumber",defaultValue =AppConstants.PAGE_NUMBER,required = false)int pagenumber,
															                @RequestParam(value = "pagesize",defaultValue =AppConstants.PAGE_SIZE,required = false)int pagesize,
															                @RequestParam(value = "sortby",defaultValue =AppConstants.SORT_BY,required = false)String sortby,
															                @RequestParam(value = "sortdir",defaultValue =AppConstants.SORT_DIR,required = false)String sortdir) 
		{
			PaginationResponse postByCategory = postservice.getPostByCategory(categoryid,pagenumber,pagesize,sortby,sortdir);
			return new ResponseEntity(postByCategory,HttpStatus.OK);
		}
		
		//GET ALL POST
		@GetMapping("/post")	
		public ResponseEntity<PaginationResponse>getallpost(	@RequestParam(value = "pagenumber",defaultValue =AppConstants.PAGE_NUMBER,required = false)int pagenumber,
			                                                                                        @RequestParam(value = "pagesize",defaultValue =AppConstants.PAGE_SIZE,required = false)int pagesize,
			                                                                                        @RequestParam(value = "sortby",defaultValue =AppConstants.SORT_BY,required = false)String sortby,
			                                                                                        @RequestParam(value = "sortdir",defaultValue =AppConstants.SORT_DIR,required = false)String sortdir
			                                                                                      ) 
		{	
			 PaginationResponse getallpost = postservice.getallpost(pagenumber,pagesize,sortby,sortdir);
			return new ResponseEntity(getallpost,HttpStatus.OK);
		}
		
		//GET POST BY ID
		@GetMapping("/post/{pid}")
		public ResponseEntity<PostDto>getpostbyid(@PathVariable int pid) {
			 PostDto getsingle = postservice.getsingle(pid);
			 
			return new ResponseEntity<PostDto>(getsingle,HttpStatus.OK);
		}
		
		//UPDATE
		@PutMapping("/post/{id}")
		public ResponseEntity<?> update(@PathVariable int id, @RequestBody PostDto postdto) {
			PostDto update = postservice.update(postdto, id);
			return ResponseEntity.ok(update);
		}
		
		//DELETE
		@DeleteMapping("/post/{pid}")
		public ResponseEntity<?>delete(@PathVariable int pid){
			postservice.deletepost(pid);
			Map m=new HashMap<>();
			m.put("message", "Post has been deleted succcesfully");
			return ResponseEntity.ok(m);
		}
		
		//SEARCHING
		@GetMapping("/post/search/{key}")
		public ResponseEntity<?> search(@PathVariable String key){
			
			List<PostDto> searchPosts = postservice.searchPosts(key);

			if(searchPosts.size()==0) {
	        	 Map m=new HashMap<>();
	        	 m.put("massage", "NO post with "+key+"  key found");
	             return  	ResponseEntity.status(HttpStatus.NOT_FOUND).body(m); 
	         }
			return ResponseEntity.ok(searchPosts);
		}
		
		//IMG UPLOAD
		@PostMapping("/post/image/upload/{postid}")
		public  ResponseEntity<?> uploadimg(@RequestParam("image") MultipartFile img,@PathVariable int postid) throws IOException {	
			
			PostDto postdto = postservice.getsingle(postid);  //get post and set img
			
			String upload = fileservice.upload(path, img);
			postdto.setImgname(upload);
			
			PostDto update = postservice.update(postdto, postid); //then update post
			
			return new ResponseEntity(update,HttpStatus.OK);
		}
		
		//GET UPLOADED IMG
		@GetMapping(value = "/post/image/uploaded/{imagename}",produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadfile(@PathVariable String imagename,HttpServletResponse res) throws IOException {
			InputStream getresponse = fileservice.getresponse(path, imagename);
			res.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(getresponse, res.getOutputStream());
		}
		
		//GET COMMENT BY POST
		@GetMapping("/comment/post/{pid}")
		public ResponseEntity<List<CommentDto>> getcomments(@PathVariable int pid){
			List<CommentDto> commentbyPost = postservice.getCommentbyPost(pid);
			return ResponseEntity.ok(commentbyPost);
		}
}
