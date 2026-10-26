package com.moa.planner.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.planner.user.dto.UserCreateRequest;
import com.moa.planner.user.dto.UserResponse;
import com.moa.planner.user.dto.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user") // 프리픽스
public class UserController {
	
	private final UserService userService;
		
	@PostMapping
	public void createUser(@RequestBody UserCreateRequest request){
		userService.createUser(request);
	}
	
	@GetMapping("/{userId}")
	public UserResponse getUser(@PathVariable("userId") Long userId) {
		return userService.getUser(userId);
	}
	
	@PutMapping("/{userId}")
	public void updateUser(@RequestBody UserUpdateRequest request, @PathVariable("userId") Long userId) {
		userService.updateUser(request, userId);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		userService.deleteUser(userId);
	}
}
