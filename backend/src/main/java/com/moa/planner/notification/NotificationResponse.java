package com.moa.planner.notification;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {

	 private Long eventId;

	 private String title;

	 private LocalDateTime startTime;
}
