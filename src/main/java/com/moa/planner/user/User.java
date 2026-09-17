package com.moa.planner.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "users") 
public class User { 
	// 클래스 = 단수형 + Pascal, 필드 = camel / DB = snake
	// Spring Boot는 camelCase를 DB에서 snake_case로 자동 변환해준다.
	
	@Id // 기본키(PK)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터를 자동으로 1씩 증가하여 저장
	private Long userId;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(length = 255)
	private String passwordHash;
	
	@Column(nullable = false, length = 50)
	private String nickname;
	
	@Column(length = 500)
	private String profileImageUrl;
	
	@Enumerated(EnumType.STRING) //enum 값을 문자열로 저장
	@Column(nullable = false)
	private UserStatus status;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;

}
