package com.moa.planner.event.dto;

import java.time.LocalDateTime;

import com.moa.planner.group.SharePermission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventUpdateRequest {

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean isAllDay;

    private String content;

    private Long labelId;

    private String recurrenceRule;

    private Integer alarmMinutesBefore;

    private Boolean isCompleted;

    private Boolean groupShared;

    private Long groupId;

    private SharePermission sharePermission; // enum
}