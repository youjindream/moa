import { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext.jsx';
import { usePlanner } from '../context/PlannerContext.jsx';

export function useCalendarEvents(month) {
  const { api, expireSession } = useAuth();
  const { revision, setError } = usePlanner();
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    let ignore = false;
    const [year, monthNumber] = month.split('-');
    setEvents([]);
    setLoading(true);
    setError('');

    api
      .getCalendar(year, monthNumber)
      .then((data) => {
        if (!ignore) setEvents(data.events || []);
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
  }, [api, month, revision, expireSession, setError]);

  return { events, loading };
}
