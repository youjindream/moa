package com.moa.planner.user;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moa.planner.user.dto.UserCreateRequest;
import com.moa.planner.user.dto.UserLoginRequest;
import com.moa.planner.user.dto.UserResponse;
import com.moa.planner.user.dto.UserUpdateRequest;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.enums.UserStatus;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder; 

	public void createUser(UserCreateRequest request) {
		
		if (request.getPassword().equals(request.getPasswordConfirm())) {
			
			if (userRepository.existsByEmail(request.getEmail())) { 
				throw new RuntimeException("이미 존재하는 이메일");
			}
			
			User user = new User();
			
			user.setEmail(request.getEmail());
			user.setPasswordHash(passwordEncoder.encode(request.getPassword())); // encode(): 입력받은 비밀번호를 해시값으로 변환
			user.setNickname(request.getNickname());
			user.setProfileImageUrl(request.getProfileImageUrl());
			user.setStatus(UserStatus.ACTIVE);
			user.setCreatedAt(LocalDateTime.now()); // now(): 현재 시간을 가져옴
			user.setUpdatedAt(LocalDateTime.now());
			
			userRepository.save(user);
			
		} else { 
			throw new RuntimeException("비밀번호 다름");
		}
	}
	
	public UserResponse getUser(Long diaryId) {
		User user = userRepository.findById(diaryId).orElseThrow();
		return new UserResponse(user);  
	}
	
	public void updateUser(UserUpdateRequest request, Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		
		user.setNickname(request.getNickname());
		user.setProfileImageUrl(request.getProfileImageUrl());
		
		userRepository.save(user);
	}
	
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
}
