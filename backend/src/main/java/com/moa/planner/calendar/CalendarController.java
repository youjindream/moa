package com.moa.planner.calendar;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moa.planner.calendar.dto.CalendarDayResponse;
import com.moa.planner.calendar.dto.CalendarResponse;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {
	
	private final CalendarService calendarService;

	@GetMapping
	public CalendarResponse getCalendar(
			@RequestParam("year") int year, // @RequestParam: URL의 ? 뒤에 있는 값을 가져옴
			@RequestParam("month") int month) { // 예시: /calendar?year=2026&month=10
		return calendarService.getCalendar(year, month); 
	}
	
	@GetMapping("/day")
	public CalendarDayResponse getCalendarDay(
			@RequestParam("date") LocalDate date) {
		return calendarService.getCalendarDay(date);
	}
	
	
			
		
}
