package com.blogapp.practice.blogappapis.services;

import java.util.List;

import com.blogapp.practice.blogappapis.payloads.PostDto;
import com.blogapp.practice.blogappapis.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	PostDto updatePost(PostDto postDto, Integer postId);

	void deletePost(Integer postId);

	PostResponse getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String sortDir);

	PostDto getPostById(Integer postId);		

	PostResponse getPostBycategory(Integer categorId, Integer pageNumber, Integer pageSize);

	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);

	List<PostDto> searchPost(String keyword);

}
