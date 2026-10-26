package com.moa.planner.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
	
	private String email;
	
	private String password;
	
	private String passwordConfirm;
	
	private String nickname;
	
	private String profileImageUrl;

}
