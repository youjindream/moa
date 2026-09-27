package com.moa.planner.chat.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventPreviewResponse {

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean isAllDay;
}