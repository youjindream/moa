package com.moa.planner.event;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.moa.planner.event.dto.EventCreateRequest;
import com.moa.planner.event.dto.EventResponse;
import com.moa.planner.event.dto.EventUpdateRequest;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final 필드 생성자 자동 생성
public class EventService {

	private final EventRepository eventRepository;
	private final UserRepository userRepository; // 임시

	public void createEvent(EventCreateRequest request) {
		Event event = new Event();

		User user = userRepository.findById(1L).orElseThrow(); // 임시
		event.setUser(user); // 임시

		event.setTitle(request.getTitle());
		event.setStartTime(request.getStartTime());
		event.setEndTime(request.getEndTime());
		event.setIsAllDay(request.getIsAllDay());
		event.setContent(request.getContent());
		event.setRecurrenceRule(request.getRecurrenceRule());
		event.setAlarmMinutesBefore(request.getAlarmMinutesBefore());
		event.setIsCompleted(false); // 완료 여부
		event.setCreatedAt(LocalDateTime.now()); // 지금 시간
		event.setUpdatedAt(LocalDateTime.now()); // 지금 시간

		eventRepository.save(event);
	}

	public EventResponse getEvent(Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow();
		return new EventResponse(event);
	}

	public void updateEvent(EventUpdateRequest request, Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow();
		event.setTitle(request.getTitle());
		event.setStartTime(request.getStartTime());
		event.setEndTime(request.getEndTime());
		event.setIsAllDay(request.getIsAllDay());
		event.setContent(request.getContent());
		event.setRecurrenceRule(request.getRecurrenceRule());
		event.setAlarmMinutesBefore(request.getAlarmMinutesBefore());
		event.setIsCompleted(request.getIsCompleted()); // 바뀜
		event.setUpdatedAt(LocalDateTime.now()); // 지금 시간
		eventRepository.save(event);
	}

	public void deleteEvent(Long eventId) { // void 리턴값 없음
		eventRepository.deleteById(eventId);
	}

}
