package com.moa.planner.diary.dto;

import java.time.LocalDate;
import java.util.List;

import com.moa.planner.diary.Diary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryResponse {
	
	public DiaryResponse(Diary diary) {
		this.setDiaryId(diary.getDiaryId());
		this.setDiaryDate(diary.getDiaryDate());
		this.setContent(diary.getContent());
		// TODO: DiaryMedia 
	}
	
	private Long diaryId;
	
	private LocalDate diaryDate;

	private String content;
	
	private List<DiaryMediaResponse> mediaFiles;
}
