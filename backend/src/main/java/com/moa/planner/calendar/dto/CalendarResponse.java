package com.moa.planner.calendar.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarResponse {

    private Integer year; 

    private Integer month; 

    private List<CalendarEventResponse> events; 

    private CalendarDayResponse selectedDay; 
}