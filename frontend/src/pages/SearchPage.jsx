import React from 'react';
import { Search } from 'lucide-react';
import { PageContainer } from '../components/layout/PageContainer.jsx';
import { ResultList } from '../components/planner/ResultList.jsx';
import { getPageTitle } from '../constants/navigation.js';
import { usePlanner } from '../context/PlannerContext.jsx';
import { useSearchResults } from '../hooks/useSearchResults.js';

export function SearchPage() {
  const { openSearchResult } = usePlanner();
  const { keyword, results, loading, submitSearch } = useSearchResults();

  function handleSubmit(event) {
    event.preventDefault();
    const value = new FormData(event.currentTarget).get('keyword');
    submitSearch(value);
  }

  return (
    <PageContainer title={getPageTitle('search')} loading={loading}>
      <section className="card page-card">
        <header>
          <div className="section-icon purple">
            <Search size={21} />
          </div>
          <h2>기록 검색</h2>
        </header>
        <form className="search-form" onSubmit={handleSubmit}>
          <input
            name="keyword"
            aria-label="검색어"
            type="search"
            placeholder="일정, 일기, 라벨 검색"
            required
            defaultValue={keyword || ''}
          />
          <button className="primary">검색</button>
        </form>
        {results && <ResultList items={results} onOpen={openSearchResult} />}
      </section>
    </PageContainer>
  );
}
