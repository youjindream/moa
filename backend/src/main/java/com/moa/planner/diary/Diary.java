package com.moa.planner.diary;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.moa.planner.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "diaries")
public class Diary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long diaryId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false) 
	private User user;
	
	@Column(nullable = false)
	private LocalDate diaryDate;
	
	@Column(nullable = false, length = 500)
	private String content;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;

}
