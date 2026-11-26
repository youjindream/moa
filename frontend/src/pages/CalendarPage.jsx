import React from 'react';
import { ChevronLeft, ChevronRight, Plus } from 'lucide-react';
import { PageContainer } from '../components/layout/PageContainer.jsx';
import { EventList } from '../components/planner/EventList.jsx';
import { DAY_LABELS } from '../constants/calendar.js';
import { getPageTitle } from '../constants/navigation.js';
import { usePlanner } from '../context/PlannerContext.jsx';
import { useCalendarEvents } from '../hooks/useCalendarEvents.js';
import { useDiaries } from '../hooks/useDiaries.js';
import { dateKey, koreanDate, today } from '../utils/date.js';

export function CalendarPage() {
  const {
    selectedDate,
    setSelectedDate,
    openNewEvent,
    openEvent,
    openDiary,
  } = usePlanner();

  const month = selectedDate.slice(0, 7);
  const { events, loading: eventsLoading } = useCalendarEvents(month);
  const { diaries, loading: diariesLoading } = useDiaries();

  const [year, monthNumber] = selectedDate.split('-').map(Number);
  const offset = (new Date(year, monthNumber - 1, 1).getDay() + 6) % 7;
  const daysInMonth = new Date(year, monthNumber, 0).getDate();
  const selectedEvents = events.filter((event) => event.startTime?.slice(0, 10) === selectedDate);
  const selectedDiary = diaries?.find((item) => item.dateTime.startsWith(selectedDate));
  const canWriteDiary = diaries !== null;

  const moveMonth = (delta) =>
    setSelectedDate(dateKey(new Date(year, monthNumber - 1 + delta, 1)));

  return (
    <PageContainer
      title={getPageTitle('calendar')}
      loading={eventsLoading || diariesLoading}
      action={
        <button className="primary" onClick={() => openNewEvent(selectedDate)}>
          <Plus size={18} />
          일정 추가
        </button>
      }
    >
      <div className="calendar-layout">
        <section className="card calendar">
          <header>
            <h2>
              {year}년 {monthNumber}월
            </h2>
            <div className="month-controls">
              <button className="icon" aria-label="이전 달" onClick={() => moveMonth(-1)}>
                <ChevronLeft size={20} />
              </button>
              <button className="secondary" onClick={() => setSelectedDate(today())}>
                오늘
              </button>
              <button className="icon" aria-label="다음 달" onClick={() => moveMonth(1)}>
                <ChevronRight size={20} />
              </button>
            </div>
          </header>

          <div className="calendar-grid">
            {DAY_LABELS.map((day) => (
              <div className="weekday" key={day}>
                {day}
              </div>
            ))}
            {Array.from({ length: offset }, (_, index) => (
              <div className="blank" key={`blank${index}`} />
            ))}
            {Array.from({ length: daysInMonth }, (_, index) => {
              const key = dateKey(new Date(year, monthNumber - 1, index + 1));
              const daily = events.filter((event) => event.startTime?.slice(0, 10) === key);
              return (
                <button
                  key={key}
                  className={`day ${key === selectedDate ? 'active' : ''}`}
                  aria-pressed={key === selectedDate}
                  onClick={() => setSelectedDate(key)}
                >
                  <strong>{index + 1}</strong>
                  {daily.slice(0, 2).map((event) => (
                    <span key={event.eventId}>{event.title}</span>
                  ))}
                  {daily.length > 2 && <small>+{daily.length - 2}</small>}
                </button>
              );
            })}
          </div>
        </section>

        <div className="day-sidebar">
          <section className="card">
            <header>
              <h2>{koreanDate(selectedDate)}</h2>
              <button
                className="icon"
                aria-label="선택한 날에 일정 추가"
                onClick={() => openNewEvent(selectedDate)}
              >
                <Plus size={20} />
              </button>
            </header>
            <EventList events={selectedEvents} onOpen={openEvent} />
          </section>

          <section className="card">
            <header>
              <h2>일기</h2>
              {canWriteDiary && (
                <button className="link" onClick={() => openDiary(selectedDate, selectedDiary)}>
                  {selectedDiary ? '열기' : '쓰기'}
                </button>
              )}
            </header>
            <p className={selectedDiary ? 'diary-excerpt' : 'empty'}>
              {!canWriteDiary
                ? '일기를 불러오는 중이에요.'
                : selectedDiary?.content || '이 날의 일기가 없어요.'}
            </p>
          </section>
        </div>
      </div>
    </PageContainer>
  );
}
