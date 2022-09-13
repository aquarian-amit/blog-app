package com.blogapp.practice.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.practice.blogappapis.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
