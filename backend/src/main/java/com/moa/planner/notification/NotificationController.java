package com.moa.planner.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moa.planner.event.Event;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NotificationController {
	
	private final NotificationService notificationService;
	
	@GetMapping("/notification")
	public List<NotificationResponse> getNotification(){
		List<NotificationResponse> responseList = new ArrayList<>();
		List<Event> alarmEvents = notificationService.getNotification();
		for(Event event : alarmEvents) {
			NotificationResponse response = new NotificationResponse();
			response.setEventId(event.getEventId());
			response.setTitle(event.getTitle());
			response.setStartTime(event.getStartTime());
			
			responseList.add(response);
		}
		
		return responseList;
	}

}
