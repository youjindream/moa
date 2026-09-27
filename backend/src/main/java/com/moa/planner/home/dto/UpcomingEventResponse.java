package com.moa.planner.home.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpcomingEventResponse {

    private Long eventId;

    private String title;

    private LocalDateTime startTime; 
}