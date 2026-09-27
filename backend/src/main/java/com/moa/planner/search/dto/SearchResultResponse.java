package com.moa.planner.search.dto;

import java.time.LocalDateTime;

import com.moa.planner.search.SearchType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResultResponse {

    private Long id;

    private SearchType type;

    private String title;

    private String content;

    private LocalDateTime dateTime;
}