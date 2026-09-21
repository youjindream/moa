package com.moa.planner.media;

import java.time.LocalDateTime;

import com.moa.planner.user.User;

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
@Table(name = "media_files")
public class MediaFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mediaId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	private TargetType targetType;
	
	private Long targetId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MediaType mediaType;
	
	@Column(nullable = false, length = 500)
	private String fileUrl;
	
	@Column(nullable = false, length = 255)
	private String originalFilename;
	
	@Column(nullable = false)
	private Long fileSizeBytes;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;
	

}
