import React from 'react';
import { Sidebar } from './Sidebar.jsx';

export function AppLayout({ children }) {
  return (
    <div className="app">
      <Sidebar />
      <main className="content">{children}</main>
    </div>
  );
}
