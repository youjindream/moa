package com.moa.planner.chat.dto;

import java.time.LocalDateTime;

import com.moa.planner.chat.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageResponse {

    private Long messageId;

    private Role role;

    private String content;

    private LocalDateTime createdAt;

    private EventPreviewResponse eventPreview;
}