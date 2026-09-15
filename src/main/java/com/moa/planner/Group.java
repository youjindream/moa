package com.moa.planner;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "groups")
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupId;
	
	@ManyToOne
	@JoinColumn(name = "owner_user_id", nullable = false)
	private User user;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;

}
