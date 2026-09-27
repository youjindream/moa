package com.moa.planner.calendar.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarDayResponse {

	private LocalDate date; 

	private List<CalendarEventResponse> events;

	private String diaryContent; 
}
