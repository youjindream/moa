package com.moa.planner.event.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.moa.planner.event.Event;
import com.moa.planner.group.SharePermission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResponse {
	
	public EventResponse(Event event) { // 나중에 검색할때도 필요하니까
	    this.setEventId(event.getEventId());
	    this.setTitle(event.getTitle());
	    this.setStartTime(event.getStartTime());
	    this.setEndTime(event.getEndTime());
	    this.setIsAllDay(event.getIsAllDay());
	    this.setContent(event.getContent());

	    if (event.getLabel() != null) {
	        this.setLabelId(event.getLabel().getLabelId());
	    }

	    this.setRecurrenceRule(event.getRecurrenceRule());
	    this.setAlarmMinutesBefore(event.getAlarmMinutesBefore());
	    this.setIsCompleted(event.getIsCompleted());

	    // TODO: Group 관련 필드 변환
	    // TODO: EventMedia → EventMediaResponse 변환
	}

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