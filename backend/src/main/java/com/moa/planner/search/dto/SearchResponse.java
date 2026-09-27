package com.moa.planner.search.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResponse {

    private Integer totalCount; // 전체 검색 결과 개수

    private Integer eventCount; // Event 검색 결과 개수

    private Integer diaryCount; // Diary 검색 결과 개수

    private Integer labelCount; // Label 검색 결과 개수

    private List<SearchResultResponse> results; // 실제 검색 결과 목록
}