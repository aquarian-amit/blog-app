package com.blogapp.practice.blogappapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.practice.blogappapis.entities.Category;
import com.blogapp.practice.blogappapis.exceptions.ResourceNotFoundException;
import com.blogapp.practice.blogappapis.payloads.CategoryDto;
import com.blogapp.practice.blogappapis.repositories.CategoryRepo;
import com.blogapp.practice.blogappapis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category newCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(newCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		this.categoryRepo.save(category);
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categoryList = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categoryList.stream()
				.map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}
