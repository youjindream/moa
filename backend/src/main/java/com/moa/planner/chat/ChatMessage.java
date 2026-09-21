package com.moa.planner.chat;

import java.time.LocalDateTime;

import com.moa.planner.media.MediaFile;
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
@Table(name = "chat_messages")
public class ChatMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@Column(columnDefinition = "TEXT") // 글자 수를 제한할 수 없이 텍스트 저장
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "media_id")
	private MediaFile mediaFile;
	
	@Column(nullable = false)
	private LocalDateTime createdAt;

}
