package com.moa.planner.event;

import java.time.LocalDateTime;

import com.moa.planner.label.Label;
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
@Table(name = "events")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "label_id")
	private Label label;
	
	@Column(nullable = false, length = 200)
	private String title;
	
	private String content;
	
	@Column(nullable = false)
	private LocalDateTime startTime;
	
	private LocalDateTime endTime;
	
	@Column(nullable = false)
	private Boolean isAllDay;
	
	private Boolean isCompleted;
	
	@Column(length = 255)
	private String recurrenceRule;
	
	private Integer alarmMinutesBefore;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
}
