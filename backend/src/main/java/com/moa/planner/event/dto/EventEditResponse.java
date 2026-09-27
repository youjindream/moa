package com.moa.planner.event.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.moa.planner.group.SharePermission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventEditResponse {

    private Long eventId;

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

    private SharePermission sharePermission;

    private List<EventMediaResponse> mediaFiles;
}