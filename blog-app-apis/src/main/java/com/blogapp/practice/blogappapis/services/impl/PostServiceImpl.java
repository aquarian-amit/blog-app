package com.blogapp.practice.blogappapis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.practice.blogappapis.entities.Category;
import com.blogapp.practice.blogappapis.entities.Post;
import com.blogapp.practice.blogappapis.entities.User;
import com.blogapp.practice.blogappapis.exceptions.ResourceNotFoundException;
import com.blogapp.practice.blogappapis.payloads.PostDto;
import com.blogapp.practice.blogappapis.payloads.PostResponse;
import com.blogapp.practice.blogappapis.repositories.CategoryRepo;
import com.blogapp.practice.blogappapis.repositories.PostRepo;
import com.blogapp.practice.blogappapis.repositories.UserRepo;
import com.blogapp.practice.blogappapis.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post addedPost = this.postRepo.save(post);
		return this.modelMapper.map(addedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);

		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
			System.out.println("Ascending Sort");
		} else {
			sort = Sort.by(sortBy).descending();
			System.out.println("Descending Sort");
		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		System.out.println("pagePostLength");
		System.out.println(pagePost.getSize());
		List<Post> posts = pagePost.getContent();

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setContent(postDtos);
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostBycategory(Integer categorId, Integer pageNumber, Integer pageSize) {

		Category category = this.categoryRepo.findById(categorId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categorId));

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost = this.postRepo.findByCategory(category, pageable);
		System.out.println("pagePostLength");
		System.out.println(pagePost.getSize());
		List<Post> posts = pagePost.getContent();

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());

		return postResponse;

	}

	@Override
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost = this.postRepo.findByUser(user, pageable);
		System.out.println("pagePostLength");
		System.out.println(pagePost.getSize());
		List<Post> posts = pagePost.getContent();

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());

		return postResponse;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}
