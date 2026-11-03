package com.moa.planner.home;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.moa.planner.diary.Diary;
import com.moa.planner.diary.DiaryRepository;
import com.moa.planner.event.Event;
import com.moa.planner.event.EventRepository;
import com.moa.planner.home.dto.HomeResponse;
import com.moa.planner.home.dto.TodayDiaryResponse;
import com.moa.planner.home.dto.TodayEventResponse;
import com.moa.planner.home.dto.UpcomingEventResponse;
import com.moa.planner.home.dto.WeeklyRhythmResponse;
import com.moa.planner.user.entity.User;
import com.moa.planner.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {

	private final UserRepository userRepository;
	private final DiaryRepository diaryRepository;
	private final EventRepository eventRepository;

	public HomeResponse getHome() {

		HomeResponse response = new HomeResponse();

		LocalDate date = LocalDate.now();
		LocalDateTime time = LocalDateTime.now();

		LocalDateTime startTime = date.atStartOfDay();
		LocalDateTime endTime = startTime.plusDays(1);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow();

		response.setNickname(user.getNickname());

		// todayEvents 만들기
		List<Event> events = eventRepository.findEventsByDateRange(user.getUserId(), startTime, endTime);

		List<TodayEventResponse> eventResponseList = new ArrayList<>();

		for (Event event : events) {
			TodayEventResponse eventResponse = getTodayEvent(event);

			eventResponseList.add(eventResponse);
		}

		response.setTodayEvents(eventResponseList);

		// todayDiary 만들기
		Optional<Diary> diary = diaryRepository.findDiaryByDate(user.getUserId(), date);

		// Optional은 null 대신 Optional.empty()를 반환
		// isPresent(): Optional 내부에 값이 존재하면 true, 없으면 false
		// isEmpty(): Optional 내부에 값이 없으면 true, 존재하면 false
		if (diary.isPresent()) {
			response.setTodayDiary(getTodayDiary(diary.get())); // diary → Optional<Diary> 타입
		} 														// diary.get() → Diary 타입

		// upcomingEvents 만들기
		List<Event> upcomingEvents = eventRepository.findUpcomingEvent(user.getUserId(), time, PageRequest.of(0, 3));

		List<UpcomingEventResponse> upcomingResponseList = new ArrayList<>();

		for (Event upcomingEvent : upcomingEvents) {

			UpcomingEventResponse upcomingResponse = getUpcomingEvent(upcomingEvent);

			upcomingResponseList.add(upcomingResponse);
		}

		response.setUpcomingEvents(upcomingResponseList);

		// weeklyRhythm 만들기
		// with(): 날짜를 지정한 조건에 맞게 조정하고, 새로운 LocalDate 객체를 반환하는 메서드
		// DayOfWeek.MONDAY: 월요일을 나타내는 Enum 상수
		LocalDate monday = date.with(DayOfWeek.MONDAY); 
				
		LocalDateTime weekStartTime = monday.atStartOfDay();
		LocalDateTime weekEndTime = weekStartTime.plusDays(7);
				
		List<Event> weekEvents = eventRepository.findEventsByDateRange(user.getUserId(), weekStartTime, weekEndTime);		
		
		response.setWeeklyRhythm(RythmResponse(weekEvents));
		
		return response;

	}

	public TodayDiaryResponse getTodayDiary(Diary diary) {
		TodayDiaryResponse response = new TodayDiaryResponse();
		response.setDiaryId(diary.getDiaryId());
		response.setDiaryDate(diary.getDiaryDate());
		response.setContent(diary.getContent());
		return response;

	}

	public TodayEventResponse getTodayEvent(Event event) {

		TodayEventResponse response = new TodayEventResponse();

		response.setEventId(event.getEventId());
		response.setTitle(event.getTitle());
		response.setStartTime(event.getStartTime());
		response.setIsCompleted(event.getIsCompleted());
		if (event.getLabel() != null) {
			response.setLabelColor(event.getLabel().getColorCode());
		}
		return response;

	}

	public UpcomingEventResponse getUpcomingEvent(Event event) {
		UpcomingEventResponse response = new UpcomingEventResponse();

		response.setEventId(event.getEventId());
		response.setTitle(event.getTitle());
		response.setStartTime(event.getStartTime());

		return response;

	}

	public WeeklyRhythmResponse RythmResponse(List<Event> events) {
		WeeklyRhythmResponse response = new WeeklyRhythmResponse();
		
		int total = events.size();
		
		int completed = 0; 
				
		for (Event event : events) {
			if(Boolean.TRUE.equals(event.getIsCompleted())) {
				completed++;
			}
		}

		List<Integer> weekCounts = new ArrayList<>();
		
		for(int i = 0 ; i < 7 ; i++) {
			weekCounts.add(0); // {0, 0, 0, 0, 0, 0, 0}
		}
		
		// getDayOfWeek(): 날짜에 해당하는 요일을 DayOfWeek Enum으로 반환
		// getValue(): 요일을 숫자로 반환 (월요일 1 ~ 일요일 7)
		// -1: List 인덱스에 맞게 월요일 0 ~ 일요일 6으로 변환
		for(Event event : events) {
			int dayIndex = event.getStartTime().getDayOfWeek().getValue() - 1;
			// get(인덱스): 해당 위치의 값을 읽음
			// set(인덱스, 새 값): 해당 위치의 값을 새 값으로 변경
			weekCounts.set(dayIndex, weekCounts.get(dayIndex) + 1); // 해당 요일의 일정 개수 1 증가
		}
		
		response.setTotalEventCount(total);
		response.setCompletedEventCount(completed);
		response.setDailyEventCounts(weekCounts);
		
		return response;
	}

}
