package com.moa.planner.home.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeResponse { 

    private String nickname; // 좋은 아침이에요 서윤님

    private List<TodayEventResponse> todayEvents; // 오늘의 Event

    private TodayDiaryResponse todayDiary; // 한 줄 일기

    private List<UpcomingEventResponse> upcomingEvents; // 다가오는 일정

    private WeeklyRhythmResponse weeklyRhythm; // 이번 주 리듬
}