package com.moa.planner.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "social_accounts")
public class SocialAccount {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long socialId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AuthProvider provider;
	

	@Column(nullable = false, length = 100)
	private String providerId;
	

	@Column(nullable = false)
	private LocalDateTime createdAt;
}
