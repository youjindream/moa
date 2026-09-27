package com.moa.planner.chat.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatPageResponse {

    private List<ChatMessageResponse> messages;
}