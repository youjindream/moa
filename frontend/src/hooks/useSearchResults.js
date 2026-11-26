import { useCallback, useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext.jsx';
import { usePlanner } from '../context/PlannerContext.jsx';

export function useSearchResults() {
  const { api, expireSession } = useAuth();
  const { revision, setError, clearFeedback } = usePlanner();
  const [query, setQuery] = useState({ keyword: null, count: 0 });
  const [results, setResults] = useState(null);
  const [loading, setLoading] = useState(false);

  const submitSearch = useCallback(
    (rawKeyword) => {
      const trimmed = rawKeyword.trim();
      if (!trimmed) return;
      clearFeedback();
      setQuery((previous) => ({ keyword: trimmed, count: previous.count + 1 }));
    },
    [clearFeedback],
  );

  useEffect(() => {
    if (query.keyword === null) return;
    let ignore = false;
    setResults(null);
    setLoading(true);
    setError('');

    api
      .search(query.keyword)
      .then((data) => {
        if (!ignore) setResults(data.results || []);
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
  }, [api, query, revision, expireSession, setError]);

  return {
    keyword: query.keyword,
    results,
    loading,
    submitSearch,
  };
}
