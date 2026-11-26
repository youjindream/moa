import { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext.jsx';
import { usePlanner } from '../context/PlannerContext.jsx';

export function useDiaries() {
  const { api, expireSession } = useAuth();
  const { revision, setError } = usePlanner();
  const [diaries, setDiaries] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    let ignore = false;
    setDiaries(null);
    setLoading(true);
    setError('');

    api
      .search('', true)
      .then((data) => {
        if (ignore) return;
        const sorted = (data.results || [])
          .filter((item) => item.type === 'DIARY')
          .sort((a, b) => b.dateTime.localeCompare(a.dateTime));
        setDiaries(sorted);
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
  }, [api, revision, expireSession, setError]);

  return { diaries, loading };
}
