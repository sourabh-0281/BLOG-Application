package com.blog.services;

import java.util.List;

import com.blog.Dto.CategoryDto;

public interface CategoryService{

	//create
	public CategoryDto create(CategoryDto c);
	
	//update
	public CategoryDto update(CategoryDto c,int id);
	
	//delete
	void  delete(int id);
	
	//get single
	CategoryDto getsingel(int id);
	
	//get all
	List<CategoryDto>getall();
}
