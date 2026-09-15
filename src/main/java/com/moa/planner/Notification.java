package com.moa.planner;

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
@Table(name = "notifications")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationId;
	
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;
	
	@Column(nullable = false, length = 500)
	private String message;
	
	@Column(nullable = false)
	private LocalDateTime scheduledAt;
	
	private LocalDateTime sentAt;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationStatus status;
	

}
