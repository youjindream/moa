package com.moa.planner.group.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMemberResponse {

    private Long userId;

    private String nickname;

    private String profileImageUrl;
}