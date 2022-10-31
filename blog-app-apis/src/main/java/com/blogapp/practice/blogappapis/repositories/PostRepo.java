package com.blogapp.practice.blogappapis.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.practice.blogappapis.entities.Category;
import com.blogapp.practice.blogappapis.entities.Post;
import com.blogapp.practice.blogappapis.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	Page<Post> findByUser(User user, Pageable pageable);

	Page<Post> findByCategory(Category category, Pageable pageable);
	
	List<Post> findByTitleContaining(String title);

}
