package com.moa.planner.diary.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryCreateRequest {

    private LocalDate diaryDate;

    private String content;
}
