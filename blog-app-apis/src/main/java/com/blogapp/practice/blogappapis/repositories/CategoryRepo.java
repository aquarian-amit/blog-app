package com.blogapp.practice.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.practice.blogappapis.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
