package com.moa.planner.diary.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryPageResponse {
	
	private List<DiaryResponse> diaries;
	
	private List<LocalDate> diaryDates;

}
