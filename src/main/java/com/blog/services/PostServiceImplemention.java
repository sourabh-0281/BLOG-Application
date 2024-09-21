package com.blog.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.Dto.CommentDto;
import com.blog.Dto.PaginationResponse;
import com.blog.Dto.PostDto;
import com.blog.Dto.UserDto;
import com.blog.entities.Category;
import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exception.ResourceNotfoundException;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;

@Service
public class PostServiceImplemention implements PostService {

	@Autowired
	private PostRepo postrepo;
	
	@Autowired
	private ModelMapper mm;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private CommentRepo commentrepo;
	
	@Autowired
	private CategoryRepo categoryrepo;
	
	//CREATE
	@Override
	public PostDto create(PostDto postdto,int userid,int categoryid) {
		
       User user = userrepo.findById(userid).orElseThrow( () -> new ResourceNotfoundException("user", "id", userid));
	   Category category = categoryrepo.findById(categoryid).orElseThrow(()->new ResourceNotfoundException("category", "id", categoryid));
	 

	  
	   Post post = mm.map(postdto,Post.class);
       post.setUser(user);
       post.setCategory(category);
       post.setImgname("default.png");
       post.setAddeddate(new Date());

        postrepo.save(post);
		return mm.map(postrepo.save(post), PostDto.class);
	}

	//UPDATE
	@Override
	public PostDto update(PostDto postdto, int id) {		
		Post post = postrepo.findById(id).orElseThrow( () -> new ResourceNotfoundException("Post", "id", id));
		post.setTitle(postdto.getTitle());
		post.setContent(postdto.getContent());
		post.setImgname(postdto.getImgname());
		Post save = postrepo.save(post);
		return mm.map(save, PostDto.class);
	}

	//GET SINGLE
	@Override
	public PostDto getsingle(int id) {
		Post post = postrepo.findById(id).orElseThrow(()->new ResourceNotfoundException("Post","id",id));
	    List<Comment> comments = commentrepo.findByPost(post).orElseThrow(()->new ResourceNotfoundException("Not found"));
		PostDto postdto = mm.map(post, PostDto.class);
		Set<CommentDto> collect = comments.stream().map(c->mm.map(c, CommentDto.class)).collect(Collectors.toSet());
		collect.forEach(c->postdto.getComment().add(c));
		return postdto;
	}

	//GET ALL POSTS
	//PAGINATION
	//SORTING
	@Override
	public PaginationResponse getallpost(int pagenumber,int pagesize,String sortby,String sortdir) {
		
		Sort sort=null;
		if(sortdir.equalsIgnoreCase("asc")) {
			sort=sort.by(sortby).ascending();
		}else {
			sort=sort.by(sortby).descending();
		}
		Pageable p=PageRequest.of(pagenumber, pagesize,sort); //there are three values here
		
		Page<Post> all = postrepo.findAll(p);  //findall is method that takes pageable object
		
		List<Post> content = all.getContent();		 
	    List<PostDto> postdto = content.stream().map(m-> mm.map(m, PostDto.class)).collect(Collectors.toList());
	    
	   PaginationResponse pg=new PaginationResponse();
	   pg.setContent(postdto);
	   pg.setPagenumber(all.getNumber());
	   pg.setPageSize(all.getSize());
	   pg.setTotalElements(all.getTotalElements());
	   pg.setTotalPages(all.getTotalPages());
	   pg.setLastpage(all.isLast());
		return pg;
	}

	//GET POSTS BY CATEGORY
	@Override
	public PaginationResponse getPostByCategory(int cid,int pagenumber,int pagesize,String sortby,String sortdir) {
		
		Category category = categoryrepo.findById(cid).orElseThrow(()->new ResourceNotfoundException("Category","id",cid));
		
		Pageable p=PageRequest.of(pagenumber, pagesize,Sort.by(sortby).ascending());
		
		Page<Post> post= postrepo.findByCategory(category,p);
		List<Post> content = post.getContent();
		
		List<PostDto >map =content.stream().map(m->mm.map(m, PostDto.class) ).collect(Collectors.toList()); 
		
		 PaginationResponse pg=new PaginationResponse();
		   pg.setContent(map);
		   pg.setPagenumber(post.getNumber());
		   pg.setPageSize(post.getSize());
		   pg.setTotalElements(post.getTotalElements());
		   pg.setTotalPages(post.getTotalPages());
		   pg.setLastpage(post.isLast());
		return pg;

	}

	//GET POST BY USER
	@Override
	public List<PostDto> getPostByUser(int uid) {
	    User user = userrepo.findById(uid).orElseThrow(()->new ResourceNotfoundException("User","id",uid));
		List<Post> post = postrepo.findByUser(user);
		
		List<PostDto> postdtos = post.stream().map(m->mm.map(m, PostDto.class)).collect(Collectors.toList());
		return postdtos;
	}

	//SEARCH
	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> post = postrepo.findByTitleContaining(keyword);		
         
		return post.stream().map(m-> mm.map(m, PostDto.class)).collect(Collectors.toList());
	}

	//DELETE
	@Override
	public void deletepost(int id) {
		Post post = postrepo.findById(id).orElseThrow(()->new ResourceNotfoundException("Post","id",id));
		postrepo.delete(post);
	}

	
	//GET POST BY COMMENT
	@Override
	public List<CommentDto> getCommentbyPost(int pid) {
	    
		Post post = postrepo.findById(pid).orElseThrow( ()->new ResourceNotfoundException("post", "id", pid));
       List<Comment> comment = commentrepo.findByPost(post).orElseThrow( ()->new ResourceNotfoundException("comment with this post not found"));
	List<CommentDto> collect = comment.stream().map(m-> mm.map(m, CommentDto.class)).collect(Collectors.toList());
		return collect;
	}


}
