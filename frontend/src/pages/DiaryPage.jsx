import React from 'react';
import { BookOpen, Plus } from 'lucide-react';
import { PageContainer } from '../components/layout/PageContainer.jsx';
import { ResultList } from '../components/planner/ResultList.jsx';
import { getPageTitle } from '../constants/navigation.js';
import { usePlanner } from '../context/PlannerContext.jsx';
import { useDiaries } from '../hooks/useDiaries.js';

export function DiaryPage() {
  const { selectedDate, setSelectedDate, openDiary, openSearchResult } = usePlanner();
  const { diaries, loading } = useDiaries();

  const selectedDiary = diaries?.find((item) => item.dateTime.startsWith(selectedDate));

  return (
    <PageContainer
      title={getPageTitle('diary')}
      loading={loading}
      action={
        <button
          className="primary"
          onClick={() => openDiary(selectedDate, selectedDiary)}
          disabled={diaries === null}
        >
          <Plus size={18} />
          일기 쓰기
        </button>
      }
    >
      <section className="card page-card">
        <header>
          <div className="section-icon peach">
            <BookOpen size={21} />
          </div>
          <h2>일기 모아보기</h2>
          <input
            aria-label="일기를 쓸 날짜"
            type="date"
            value={selectedDate}
            onChange={(event) => {
              if (event.target.value) setSelectedDate(event.target.value);
            }}
          />
        </header>
        {diaries && <ResultList items={diaries} onOpen={openSearchResult} />}
      </section>
    </PageContainer>
  );
}
