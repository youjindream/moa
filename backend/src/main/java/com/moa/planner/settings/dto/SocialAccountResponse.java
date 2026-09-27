package com.moa.planner.settings.dto;

import com.moa.planner.user.enums.AuthProvider;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialAccountResponse {

    private AuthProvider provider;

    private Boolean connected;
}