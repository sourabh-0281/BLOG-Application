package com.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Dto.CategoryDto;
import com.blog.entities.Category;
import com.blog.exception.ResourceNotfoundException;
import com.blog.repositories.CategoryRepo;

@Service
public class CategoryServiceImplementation implements CategoryService{

	@Autowired
	private CategoryRepo categoryrepo;
	
	@Autowired
	private ModelMapper mm;

	//create
	@Override
	public CategoryDto create(CategoryDto c) {	
		Category map = mm.map(c, Category.class);
		Category save = categoryrepo.save(map);
		return  mm.map(save, CategoryDto.class);
	}

	//update
	@Override
	public CategoryDto update(CategoryDto c, int id) {
		Category category = categoryrepo.findById(id).orElseThrow(()->new ResourceNotfoundException("category","id", id));
		category.setTitle(c.getTitle());
		category.setDescription(c.getDescription());		
		return mm.map(categoryrepo.save(category),CategoryDto.class );
	}

	//delete
	@Override
	public void delete(int id) {
		Category category = categoryrepo.findById(id).orElseThrow(()->new ResourceNotfoundException("category","id", id));
		categoryrepo.delete(category);
		
	}

	//getsingle
	@Override
	public CategoryDto getsingel(int id) {
		Category category = categoryrepo.findById(id).orElseThrow(()->new ResourceNotfoundException("category","id", id));
		return mm.map(category, CategoryDto.class);
	}

	//get all
	@Override
	public List<CategoryDto> getall() {
         List<Category> all = categoryrepo.findAll();
         if (all==null) {
		    	throw  new ResourceNotfoundException("No users found");
		}
         return all.stream().map(
        		 m-> mm.map(m, CategoryDto.class)
         ).collect(Collectors.toList());		
	}


}
