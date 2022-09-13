package com.blogapp.practice.blogappapis.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.practice.blogappapis.payloads.ApiResponse;
import com.blogapp.practice.blogappapis.payloads.CategoryDto;
import com.blogapp.practice.blogappapis.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDto> careateCategory(@RequestBody @Valid CategoryDto categoryDto) {
		CategoryDto newCateoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(newCateoryDto, HttpStatus.CREATED);
	}

	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto,
			@PathVariable Integer catId) {
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}

	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted successfully", true),
				HttpStatus.OK);
	}

	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		CategoryDto categoryDto = this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> categoriesDtoList = this.categoryService.getCategories();
		return ResponseEntity.ok(categoriesDtoList);
	}
}
