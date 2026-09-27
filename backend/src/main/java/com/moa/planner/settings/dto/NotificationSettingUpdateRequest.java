package com.moa.planner.settings.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationSettingUpdateRequest {

    private Boolean eventNotificationEnabled;

    private Boolean notificationSoundEnabled;

    private Boolean weeklySummaryEnabled;
}