import React, { useEffect, useState } from 'react';
import { ArrowRight, BookOpen, CalendarDays, Clock3, Plus, Sparkles } from 'lucide-react';
import { PageContainer } from '../components/layout/PageContainer.jsx';
import { EventList } from '../components/planner/EventList.jsx';
import { DAY_LABELS } from '../constants/calendar.js';
import { getPageTitle } from '../constants/navigation.js';
import { useAuth } from '../context/AuthContext.jsx';
import { usePlanner } from '../context/PlannerContext.jsx';
import { eventTime, koreanDate, today } from '../utils/date.js';

export function HomePage() {
  const { api, home, setHome, expireSession } = useAuth();
  const {
    revision,
    setError,
    goToPage,
    openNewEvent,
    openEvent,
    openDiary,
    toggleEvent,
  } = usePlanner();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    let ignore = false;
    setLoading(true);
    setError('');

    api
      .getHome()
      .then((data) => {
        if (!ignore) setHome(data);
      })
      .catch((failure) => {
        if (ignore) return;
        if (failure.status === 401) expireSession();
        else setError(failure.message);
      })
      .finally(() => {
        if (!ignore) setLoading(false);
      });

    return () => {
      ignore = true;
    };
  }, [api, revision, setHome, expireSession, setError]);

  const rhythm = home.weeklyRhythm || {};
  const count = rhythm.totalEventCount || 0;
  const complete = rhythm.completedEventCount || 0;
  const days = rhythm.dailyEventCounts || [];
  const currentDate = today();

  return (
    <PageContainer
      title={getPageTitle('home')}
      loading={loading}
      action={
        <button className="primary" onClick={() => openNewEvent(currentDate)}>
          <Plus size={18} />
          일정 추가
        </button>
      }
    >
      <div className="hero">
        <div>
          <p>
            <CalendarDays size={17} /> {koreanDate(currentDate)}
          </p>
          <h1>{home.nickname}님, 안녕하세요!</h1>
          <div>오늘의 일정과 기록을 한눈에 살펴보세요.</div>
        </div>
        <div className="hero-art" aria-hidden="true">
          <div />
          <Sparkles size={75} strokeWidth={1} />
          <span />
          <i />
        </div>
      </div>

      <div className="dashboard-grid">
        <section className="card">
          <header>
            <div className="section-icon purple">
              <CalendarDays size={21} />
            </div>
            <h2>오늘의 일정</h2>
            <button className="link" onClick={() => goToPage('calendar')}>
              캘린더 <ArrowRight size={15} />
            </button>
          </header>
          <EventList events={home.todayEvents} onOpen={openEvent} onToggle={toggleEvent} />
        </section>

        <section className="card">
          <header>
            <div className="section-icon green">
              <Sparkles size={21} />
            </div>
            <h2>이번 주 리듬</h2>
          </header>
          <p className="stat">
            <strong>{complete}</strong> / {count}개 일정 완료
          </p>
          <div className="chart" aria-label="월요일부터 일요일까지 일정 수">
            {DAY_LABELS.map((day, index) => (
              <div key={day}>
                <div className="bar-track">
                  <span
                    title={`${day}요일 ${days[index] || 0}개`}
                    style={{
                      height: `${(100 * (days[index] || 0)) / Math.max(1, ...days)}%`,
                    }}
                  />
                </div>
                <small>{day}</small>
              </div>
            ))}
          </div>
        </section>

        <section className="card">
          <header>
            <div className="section-icon peach">
              <BookOpen size={21} />
            </div>
            <h2>오늘의 일기</h2>
            <button className="link" onClick={() => openDiary(currentDate, home.todayDiary)}>
              {home.todayDiary ? '열기' : '쓰기'} <ArrowRight size={15} />
            </button>
          </header>
          <p className={home.todayDiary ? 'diary-excerpt' : 'empty'}>
            {home.todayDiary?.content || '아직 작성한 일기가 없어요.'}
          </p>
        </section>

        <section className="card">
          <header>
            <div className="section-icon blue">
              <Clock3 size={21} />
            </div>
            <h2>다가오는 일정</h2>
          </header>
          {home.upcomingEvents?.length ? (
            home.upcomingEvents.map((event) => (
              <button
                key={event.eventId}
                className="upcoming"
                onClick={() => openEvent(event.eventId)}
              >
                <span>{event.title}</span>
                <small>
                  {event.startTime?.slice(5, 10).replace('-', '/')} · {eventTime(event)}
                </small>
              </button>
            ))
          ) : (
            <p className="empty">다가오는 일정이 없어요.</p>
          )}
        </section>
      </div>
    </PageContainer>
  );
}
