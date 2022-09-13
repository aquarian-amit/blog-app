package com.blogapp.practice.blogappapis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.practice.blogappapis.payloads.ApiResponse;
import com.blogapp.practice.blogappapis.payloads.PostDto;
import com.blogapp.practice.blogappapis.payloads.PostResponse;
import com.blogapp.practice.blogappapis.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPostDto, HttpStatus.CREATED);
	}

	@GetMapping("user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		List<PostDto> postDtos = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);

	}

	@GetMapping("category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postDtos = this.postService.getPostBycategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	@GetMapping("posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize) {
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	@GetMapping("post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	@DeleteMapping("post/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted Successfuly", true), HttpStatus.OK);
	}

	@PutMapping("post/{postId}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
}
