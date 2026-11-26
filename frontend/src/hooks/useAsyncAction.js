import { useCallback, useRef, useState } from 'react';

export function useAsyncAction({ onStart, onUnauthorized, onError }) {
  const [actionBusy, setActionBusy] = useState(false);
  const busyRef = useRef(false);

  const runAction = useCallback(
    async (task) => {
      if (busyRef.current) return;
      busyRef.current = true;
      setActionBusy(true);
      onStart?.();
      try {
        await task();
      } catch (failure) {
        if (failure.status === 401) {
          onUnauthorized?.();
        } else {
          onError?.(failure.message);
        }
      } finally {
        busyRef.current = false;
        setActionBusy(false);
      }
    },
    [onStart, onUnauthorized, onError],
  );

  return { actionBusy, runAction };
}
