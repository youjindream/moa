package com.moa.planner.settings.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequest {

    private String nickname;

    private String email;

    private String profileImageUrl;
}