package com.moa.planner.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequest {

    private String content;

    private Long mediaId;
}