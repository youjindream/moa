package com.moa.planner.notification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.moa.planner.event.Event;
import com.moa.planner.event.EventRepository;
import com.moa.planner.event.dto.EventResponse;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final EventRepository eventRepository;
	private final UserRepository userRepository;

	public List<Event> getNotification() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow();
		Long userId = user.getUserId();
		
		LocalDateTime now = LocalDateTime.now();
		
		// 현재시간 이후에 있는 일정을 가져옴
		List<Event> events = eventRepository.findAlarmEvents(userId, now);
		List<Event> alarmEvents = new ArrayList<>();
		
 		for(Event event : events) {
 			LocalDateTime alarmTime = event.getStartTime().minusMinutes(event.getAlarmMinutesBefore());
 			
 			// 현재시간이 알림시간보다 이전이 아니면 참
 			// A.isBefore(B) = A가 B보다 이전이니?
 			if (!now.isBefore(alarmTime)) {
 				alarmEvents.add(event);
 			}
		}
		return alarmEvents;
	}
		
}