package com.moa.planner.home.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodayDiaryResponse {

    private Long diaryId;

    private LocalDate diaryDate;

    private String content;
}