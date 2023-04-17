package com.blogapp.practice.blogappapis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapp.practice.blogappapis.entities.User;
import com.blogapp.practice.blogappapis.exceptions.ResourceNotFoundException;
import com.blogapp.practice.blogappapis.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// loading user from database by username.
		User user =  userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Email :" + username, 0));
		
		return user;
	}

}
