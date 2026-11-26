import React, { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import { useAsyncAction } from '../hooks/useAsyncAction.js';
import { today } from '../utils/date.js';
import { useAuth } from './AuthContext.jsx';

const PlannerContext = createContext(null);

export function PlannerProvider({ children }) {
  const { api, signedIn, expireSession, signOut } = useAuth();
  const [page, setPage] = useState('home');
  const [selectedDate, setSelectedDate] = useState(today);
  const [editor, setEditor] = useState(null);
  const [revision, setRevision] = useState(0);
  const [error, setError] = useState('');
  const [notice, setNotice] = useState('');

  const clearFeedback = useCallback(() => {
    setError('');
    setNotice('');
  }, []);

  const { actionBusy, runAction } = useAsyncAction({
    onStart: clearFeedback,
    onUnauthorized: expireSession,
    onError: setError,
  });

  useEffect(() => {
    if (!signedIn) {
      setPage('home');
      setSelectedDate(today());
      setEditor(null);
      setError('');
      setNotice('');
    }
  }, [signedIn]);

  const goToPage = useCallback(
    (nextPage) => {
      clearFeedback();
      setPage(nextPage);
    },
    [clearFeedback],
  );

  const notifyChanged = useCallback((message) => {
    setEditor(null);
    setNotice(message);
    setRevision((previous) => previous + 1);
  }, []);

  const closeEditor = useCallback(() => {
    setEditor(null);
  }, []);

  const openNewEvent = useCallback((day) => {
    setEditor({ kind: 'event', date: day });
  }, []);

  const openEvent = useCallback(
    (id) =>
      runAction(async () => {
        const item = await api.getEvent(id);
        setEditor({ kind: 'event', item, date: item.startTime.slice(0, 10) });
      }),
    [api, runAction],
  );

  const toggleEvent = useCallback(
    (id) =>
      runAction(async () => {
        const item = await api.getEvent(id);
        await api.saveEvent(id, { ...item, isCompleted: !item.isCompleted });
        notifyChanged(item.isCompleted ? '완료 표시를 취소했어요.' : '일정을 완료했어요.');
      }),
    [api, notifyChanged, runAction],
  );

  const openDiary = useCallback(
    (day, entry) =>
      runAction(async () => {
        const id = entry?.diaryId ?? entry?.id;
        const item = id ? await api.getDiary(id) : undefined;
        setEditor({ kind: 'diary', item, date: day });
      }),
    [api, runAction],
  );

  const openSearchResult = useCallback(
    (item) => {
      if (item.type === 'EVENT') {
        openEvent(item.id);
      } else if (item.type === 'DIARY') {
        openDiary(item.dateTime.slice(0, 10), item);
      } else if (item.type === 'LABEL') {
        runAction(async () => {
          const label = await api.getLabel(item.id);
          setEditor({ kind: 'label', item: label });
        });
      }
    },
    [api, openDiary, openEvent, runAction],
  );

  const handleSignOut = useCallback(
    () => runAction(() => signOut()),
    [runAction, signOut],
  );

  const value = useMemo(
    () => ({
      page,
      goToPage,
      selectedDate,
      setSelectedDate,
      editor,
      closeEditor,
      revision,
      notifyChanged,
      error,
      setError,
      notice,
      clearFeedback,
      actionBusy,
      openNewEvent,
      openEvent,
      toggleEvent,
      openDiary,
      openSearchResult,
      handleSignOut,
    }),
    [
      page,
      goToPage,
      selectedDate,
      editor,
      closeEditor,
      revision,
      notifyChanged,
      error,
      notice,
      clearFeedback,
      actionBusy,
      openNewEvent,
      openEvent,
      toggleEvent,
      openDiary,
      openSearchResult,
      handleSignOut,
    ],
  );

  return <PlannerContext.Provider value={value}>{children}</PlannerContext.Provider>;
}

export function usePlanner() {
  const context = useContext(PlannerContext);
  if (!context) {
    throw new Error('usePlanner must be used within PlannerProvider');
  }
  return context;
}
