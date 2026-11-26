import React from 'react';
import { EditorModal } from './components/editor/EditorModal.jsx';
import { AppLayout } from './components/layout/AppLayout.jsx';
import { AuthProvider, useAuth } from './context/AuthContext.jsx';
import { PlannerProvider, usePlanner } from './context/PlannerContext.jsx';
import { AuthPage } from './pages/AuthPage.jsx';
import { CalendarPage } from './pages/CalendarPage.jsx';
import { DiaryPage } from './pages/DiaryPage.jsx';
import { HomePage } from './pages/HomePage.jsx';
import { SearchPage } from './pages/SearchPage.jsx';
import { StartupPage } from './pages/StartupPage.jsx';

const PAGE_COMPONENTS = {
  home: HomePage,
  calendar: CalendarPage,
  diary: DiaryPage,
  search: SearchPage,
};

function AuthenticatedApp() {
  const { page } = usePlanner();
  const ActivePage = PAGE_COMPONENTS[page] || HomePage;

  return (
    <>
      <AppLayout>
        <ActivePage />
      </AppLayout>
      <EditorModal />
    </>
  );
}

function AppContent() {
  const { startup, signedIn } = useAuth();

  if (startup) return <StartupPage />;
  if (!signedIn) return <AuthPage />;
  return <AuthenticatedApp />;
}

export function App() {
  return (
    <AuthProvider>
      <PlannerProvider>
        <AppContent />
      </PlannerProvider>
    </AuthProvider>
  );
}
