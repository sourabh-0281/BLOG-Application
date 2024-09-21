package com.blog.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.blog.Dto.CategoryDto;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService cs;
	
	//create
	@PostMapping("")
	public ResponseEntity<CategoryDto>create(@Valid @RequestBody CategoryDto c,BindingResult res){
		if(res.hasErrors()) {
			System.out.println(res.getAllErrors().toString());
		}
		CategoryDto categoryDto = cs.create(c);
		return ResponseEntity.ok(categoryDto);
	}
	
	//update
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto>update(@Valid @RequestBody CategoryDto c,@PathVariable int id){
		CategoryDto categoryDto = cs.update(c, id);
		return ResponseEntity.ok(categoryDto);
	}
	
	//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?>delete(@PathVariable int id){
		 cs.delete(id);
		 Map m=new HashMap<>();
		 m.put("message", "Deleted succesfully");
		return new ResponseEntity(m,HttpStatus.OK);
	}
	
	//get
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto>update(@PathVariable int id){
		CategoryDto categoryDto = cs.getsingel(id);
		return ResponseEntity.ok(categoryDto);
	}
	
	//get all
	@GetMapping("")
	public ResponseEntity<List<CategoryDto>>update(){
		 List<CategoryDto> get = cs.getall();
		return new ResponseEntity(get,HttpStatus.OK);
	}
}
