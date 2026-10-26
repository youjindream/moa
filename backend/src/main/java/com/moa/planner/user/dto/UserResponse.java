package com.moa.planner.user.dto;

import java.time.LocalDateTime;

import com.moa.planner.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
	
	public UserResponse(User user) {
		this.setEmail(user.getEmail());
		this.setNickname(user.getNickname());
		this.setProfileImageUrl(user.getProfileImageUrl());
		this.setCreatedAt(user.getCreatedAt());
	}
	
	private String email;
	
	private String nickname;
	
	private String profileImageUrl;
	
	private LocalDateTime createdAt;

}
