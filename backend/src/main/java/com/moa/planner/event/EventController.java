package com.moa.planner.event;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moa.planner.event.dto.EventCreateRequest;
import com.moa.planner.event.dto.EventResponse;
import com.moa.planner.event.dto.EventUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController // 이 컨트롤러는 화면이 아니라 JSON 응답을 주는 API 컨트롤러임을 알림
@RequiredArgsConstructor
public class EventController {

	private final EventService eventService;

	@PostMapping("/event")
	public void createEvent(@RequestBody EventCreateRequest request) { // @RequestBody: JSON 데이터를 자바 객체로 받아줌
		eventService.createEvent(request);
	}

	@GetMapping("/event/{eventId}")
	public EventResponse getEvent(@PathVariable("eventId") Long eventId) {
		return eventService.getEvent(eventId);
	}

	@PutMapping("/event/{eventId}")
	public void updateEvent(@RequestBody EventUpdateRequest request, @PathVariable("eventId") Long eventId) {
		eventService.updateEvent(request, eventId);
	}

	@DeleteMapping("/event/{eventId}")
	public void deleteEvent(@PathVariable("eventId") Long eventId) { // @PathVariable: URL 값을 변수로 받음
		eventService.deleteEvent(eventId);
	}

}