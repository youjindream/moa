import React from 'react';
import { Brand } from '../components/common/Brand.jsx';

export function StartupPage() {
  return (
    <main className="startup">
      <Brand />
      <p role="status">MOA를 여는 중이에요…</p>
    </main>
  );
}
