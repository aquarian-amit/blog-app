package com.blogapp.practice.blogappapis.services;

import java.util.List;

import com.blogapp.practice.blogappapis.payloads.PostDto;
import com.blogapp.practice.blogappapis.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	PostDto updatePost(PostDto postDto, Integer postId);

	void deletePost(Integer postId);

	PostResponse getAllPost(Integer pageSize, Integer pageNumber);

	PostDto getPostById(Integer postId);

	List<PostDto> getPostBycategory(Integer categorId);

	List<PostDto> getPostByUser(Integer userId);

	List<PostDto> searchPost(String keyword);

}
