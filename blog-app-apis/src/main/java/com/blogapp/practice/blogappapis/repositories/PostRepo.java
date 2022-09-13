package com.blogapp.practice.blogappapis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.practice.blogappapis.entities.Category;
import com.blogapp.practice.blogappapis.entities.Post;
import com.blogapp.practice.blogappapis.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);

}
