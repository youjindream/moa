package com.moa.planner.settings.dto;

import java.util.List;

import com.moa.planner.user.enums.BgType;
import com.moa.planner.user.enums.ThemeMode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsResponse {

    private ThemeMode themeMode;

    private BgType bgType;

    private String bgValue;

    private String labelColor;

    private Boolean eventNotificationEnabled;

    private Boolean notificationSoundEnabled;

    private Boolean weeklySummaryEnabled;

    private String nickname;

    private String email;

    private String profileImageUrl;

    private List<SocialAccountResponse> socialAccounts;
}