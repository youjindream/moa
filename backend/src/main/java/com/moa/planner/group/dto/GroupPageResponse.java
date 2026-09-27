package com.moa.planner.group.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupPageResponse {

    private List<GroupResponse> groups; 
}