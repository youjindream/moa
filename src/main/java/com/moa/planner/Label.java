package com.moa.planner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "labels")
public class Label {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long labelId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false, length = 50)
	private String title;
	
	@Column(nullable = false, length = 10)
	private String colorCode;

}
