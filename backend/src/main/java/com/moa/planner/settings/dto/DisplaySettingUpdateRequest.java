package com.moa.planner.settings.dto;

import com.moa.planner.user.enums.BgType;
import com.moa.planner.user.enums.ThemeMode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisplaySettingUpdateRequest {

    private ThemeMode themeMode;

    private BgType bgType;

    private String bgValue;

    private String labelColor;
}