package com.moa.planner.home.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeeklyRhythmResponse {

	private Integer totalEventCount; 
	
	private Integer completedEventCount; 
	
	private List<Integer> dailyEventCounts; 
}