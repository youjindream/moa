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
@Table(name = "group_members")
public class GroupMember {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupMemberId;
	
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false)
	private LocalDateTime joinedAt;
	
	
}
