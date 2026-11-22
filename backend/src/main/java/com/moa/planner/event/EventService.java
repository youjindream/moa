package com.moa.planner.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.moa.planner.event.dto.EventCreateRequest;
import com.moa.planner.event.dto.EventMediaResponse;
import com.moa.planner.event.dto.EventResponse;
import com.moa.planner.event.dto.EventUpdateRequest;
import com.moa.planner.label.Label;
import com.moa.planner.label.LabelRepository;
import com.moa.planner.media.MediaFile;
import com.moa.planner.media.MediaFileRepository;
import com.moa.planner.media.TargetType;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final 필드 생성자 자동 생성
public class EventService {

	private final EventRepository eventRepository;
	private final UserRepository userRepository;
	private final LabelRepository labelRepository;
	private final MediaFileRepository mediaFileRepository;

	public void createEvent(EventCreateRequest request) {

		// Authentication: 사용자 인증 정보 인터페이스
		// SecurityContextHolder: 현재 보안 정보를 관리하는 클래스
		// getContext(): 현재 보안 컨텍스트 조회
		// getAuthentication(): 인증 정보 조회
		// getName(): 인증된 사용자 이름 조회 (MOA에서는 이메일)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow();

		Event event = new Event();

		event.setUser(user);
		if (request.getLabelId() != null) {
			Label label = labelRepository.findById(request.getLabelId()).orElseThrow();
			event.setLabel(label);
		}
		event.setTitle(request.getTitle());
		event.setStartTime(request.getStartTime());
		event.setEndTime(request.getEndTime());
		event.setIsAllDay(request.getIsAllDay());
		event.setContent(request.getContent());
		event.setRecurrenceRule(request.getRecurrenceRule());
		event.setAlarmMinutesBefore(request.getAlarmMinutesBefore());
		event.setIsCompleted(false); 
		event.setCreatedAt(LocalDateTime.now());
		event.setUpdatedAt(LocalDateTime.now());

		eventRepository.save(event);
	}

	public EventResponse getEvent(Long eventId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if (!email.equals(event.getUser().getEmail())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		List<MediaFile> mediaFiles = new ArrayList<>();
		mediaFiles = mediaFileRepository.findMediaFiles(email, TargetType.EVENT, eventId);
		
		List<EventMediaResponse> mediaResponses = new ArrayList<>();
	
		for(MediaFile mediaFile : mediaFiles) {
			EventMediaResponse mediaResponse = new EventMediaResponse();
			mediaResponse.setMediaId(mediaFile.getMediaId());
			mediaResponse.setMediaType(mediaFile.getMediaType());
			mediaResponse.setFileUrl(mediaFile.getFileUrl());
			mediaResponse.setOriginalFilename(mediaFile.getOriginalFilename());
			mediaResponse.setFileSizeBytes(mediaFile.getFileSizeBytes());
			
			mediaResponses.add(mediaResponse);
		}
		
		EventResponse response = new EventResponse(event, mediaResponses);
		
		return response;
	}

	public void updateEvent(EventUpdateRequest request, Long eventId) {
		
		Event event = eventRepository.findById(eventId).orElseThrow();
		
		if(request.getLabelId() != null) {
			Label label = labelRepository.findById(request.getLabelId()).orElseThrow();
			event.setLabel(label);
		} else { 
			event.setLabel(null);
		}
		
		event.setTitle(request.getTitle());
		event.setStartTime(request.getStartTime());
		event.setEndTime(request.getEndTime());
		event.setIsAllDay(request.getIsAllDay());
		event.setContent(request.getContent());
		event.setRecurrenceRule(request.getRecurrenceRule());
		event.setAlarmMinutesBefore(request.getAlarmMinutesBefore());
		event.setIsCompleted(request.getIsCompleted()); 
		event.setUpdatedAt(LocalDateTime.now()); 
		eventRepository.save(event);
	} 
	

	public void deleteEvent(Long eventId) {
		eventRepository.deleteById(eventId);
	}

}
