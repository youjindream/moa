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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_settings")
public class UserSetting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long settingId;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ThemeMode themeMode;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BgType bgType;
	
	@Column(length = 500)
	private String bgValue;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
}
