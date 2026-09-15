package com.moa.planner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long profileId;
	
	@OneToOne // 현재 클래스 To 필드 타입 (현재 클래스 : 필드 타입)
	@JoinColumn(name = "user_id", nullable = false) // 외래키(FK)
	private User user;
	
	@Column(length = 50)
	private String realName;
	
	@Column(length = 20)
	private String phoneNumber;
	
	private LocalDate birthDate;
	
	@Enumerated(EnumType.STRING)
	private GenderType gender;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
}
