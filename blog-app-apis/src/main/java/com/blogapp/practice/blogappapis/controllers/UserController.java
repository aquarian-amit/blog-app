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
import com.blogapp.practice.blogappapis.payloads.UserDto;
import com.blogapp.practice.blogappapis.services.UserService;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto,
			@PathVariable("userId") Integer userId) {
		UserDto updateUserDto = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updateUserDto);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

}
