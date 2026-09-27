package com.moa.planner.search.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

    private String keyword;

    private Boolean searchTitle;

    private Boolean searchContent;

    private Boolean searchDiary;

    private Boolean searchLabel;
}