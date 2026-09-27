package com.moa.planner.calendar.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarEventResponse {

    private Long eventId; 
    
    private String title; 
    
    private LocalDateTime startTime; 
    
    private LocalDateTime endTime; 
    
    private Boolean isAllDay;
    
    private Boolean isCompleted; 
    
    private String labelColor; 
}