import React, { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import { createDemoClient } from '../api/demoClient.js';
import { createSampleHome } from '../api/demoData.js';
import { httpRequest } from '../api/httpClient.js';
import { createPlannerApi } from '../api/plannerApi.js';

const AuthContext = createContext(null);
const httpApi = createPlannerApi(httpRequest);

export function AuthProvider({ children }) {
  const [home, setHome] = useState(null);
  const [demoTransport, setDemoTransport] = useState(null);
  const [startup, setStartup] = useState(true);
  const [startupError, setStartupError] = useState('');

  const isDemo = Boolean(demoTransport);
  const api = useMemo(
    () => (demoTransport ? createPlannerApi(demoTransport) : httpApi),
    [demoTransport],
  );

  useEffect(() => {
    httpApi
      .getHome()
      .then(setHome)
      .catch((failure) => {
        if (failure.status !== 401) setStartupError(failure.message);
      })
      .finally(() => setStartup(false));
  }, []);

  const clearSession = useCallback(() => {
    setDemoTransport(null);
    setHome(null);
  }, []);

  const expireSession = useCallback(() => {
    clearSession();
    setStartupError('세션이 만료됐어요. 다시 로그인해 주세요.');
  }, [clearSession]);

  const signIn = useCallback(async (email, password) => {
    const data = await httpApi.login(email, password);
    setStartupError('');
    setHome(data);
  }, []);

  const register = useCallback((values) => httpApi.register(values), []);

  const startDemo = useCallback(async () => {
    const transport = createDemoClient(createSampleHome());
    const demoApi = createPlannerApi(transport);
    setDemoTransport(() => transport);
    setStartupError('');
    setHome(await demoApi.getHome());
  }, []);

  const signOut = useCallback(async () => {
    if (!isDemo) {
      await httpApi.logout();
    }
    setStartupError('');
    clearSession();
  }, [isDemo, clearSession]);

  const value = useMemo(
    () => ({
      home,
      setHome,
      startup,
      startupError,
      isDemo,
      signedIn: Boolean(home),
      api,
      signIn,
      register,
      startDemo,
      signOut,
      expireSession,
    }),
    [home, startup, startupError, isDemo, api, signIn, register, startDemo, signOut, expireSession],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
}
