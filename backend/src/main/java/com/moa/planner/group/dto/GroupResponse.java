package com.moa.planner.group.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupResponse {

    private Long groupId;

    private String name;

    private Integer memberCount; // 그룹 멤버 수

    private Integer sharedEventCount; // 그룹에 공유된 일정 수

    private List<GroupMemberResponse> members; // 그룹 멤버 목록
}
