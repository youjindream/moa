package com.moa.planner.group;

import java.time.LocalDateTime;

import com.moa.planner.event.Event;

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
@Table(name = "event_group_shares")
public class EventGroupShare {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shareId;
	
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;
	
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SharePermission permission;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
}
