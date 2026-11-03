package com.moa.planner.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.moa.planner.calendar.dto.CalendarDayResponse;
import com.moa.planner.calendar.dto.CalendarEventResponse;
import com.moa.planner.calendar.dto.CalendarResponse;
import com.moa.planner.diary.Diary;
import com.moa.planner.diary.DiaryRepository;
import com.moa.planner.event.Event;
import com.moa.planner.event.EventRepository;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CalendarService {

	private final EventRepository eventRepository;
	private final UserRepository userRepository;
	private final DiaryRepository diaryRepository;

	public CalendarResponse getCalendar(int year, int month) {
		// of() : 전달받은 연도, 월, 일로 날짜 객체를 생성하는 메서드
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.plusMonths(1);

		// atStartOfDay() : 해당 날짜의 시간을 00:00으로 설정해서 LocalDateTime으로 변환하는 메서드
		LocalDateTime startTime = startDate.atStartOfDay();
		LocalDateTime endTime = endDate.atStartOfDay();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow();
		Long userId = user.getUserId();

		List<Event> events = eventRepository.findEventsByDateRange(userId, startTime, endTime);

		CalendarResponse response = new CalendarResponse();

		response.setYear(year);
		response.setMonth(month);

		// CalendarResponse의 events 부분 만들기
		List<CalendarEventResponse> eventResponseList = new ArrayList<>();

		for (Event event : events) {
			eventResponseList.add(getCalendarEvent(event));
		}

		response.setEvents(eventResponseList);

		return response;
	}

	public CalendarEventResponse getCalendarEvent(Event event) {
		CalendarEventResponse response = new CalendarEventResponse();

		response.setEventId(event.getEventId());
		response.setTitle(event.getTitle());
		response.setStartTime(event.getStartTime());
		response.setEndTime(event.getEndTime());
		response.setIsAllDay(event.getIsAllDay());
		response.setIsCompleted(event.getIsCompleted());

		if (event.getLabel() != null) {
			response.setLabelColor(event.getLabel().getColorCode());
		}

		return response;
	}

	public CalendarDayResponse getCalendarDay(LocalDate date) {

		LocalDate startDate = date;
		LocalDate endDate = startDate.plusDays(1);

		LocalDateTime startTime = startDate.atStartOfDay();
		LocalDateTime endTime = endDate.atStartOfDay();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow();

		CalendarDayResponse response = new CalendarDayResponse();
		response.setDate(date);

		List<CalendarEventResponse> eventResponseList = new ArrayList<>();

		List<Event> events = eventRepository.findEventsByDateRange(user.getUserId(), startTime, endTime);

		for (Event event : events) {
			eventResponseList.add(getCalendarEvent(event));
		}

		response.setEvents(eventResponseList);

		// Optional: 값이 없을 가능성을 표현하는 클래스
		// orElse(null): 값이 없으면 null 반환
		Diary diary = diaryRepository.findDiaryByDate(user.getUserId(), date).orElse(null);

		if (diary != null) {
			response.setDiaryContent(diary.getContent());
		}

		return response;

	}
}
