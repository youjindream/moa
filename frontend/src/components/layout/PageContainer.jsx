import React from 'react';
import { Sparkles } from 'lucide-react';
import { useAuth } from '../../context/AuthContext.jsx';
import { usePlanner } from '../../context/PlannerContext.jsx';
import { ErrorMessage } from '../common/ErrorMessage.jsx';

export function PageContainer({ title, action = null, loading = false, children }) {
  const { isDemo } = useAuth();
  const { error, notice, actionBusy } = usePlanner();

  return (
    <>
      <div className="topbar">
        <span>{title}</span>
        {action}
      </div>
      {isDemo && (
        <p className="demo-banner">
          <Sparkles size={16} /> 예시 모드 · 변경 내용은 서버에 저장되지 않아요.
        </p>
      )}
      <ErrorMessage message={error} />
      {notice && (
        <p role="status" className="notice">
          {notice}
        </p>
      )}
      {(loading || actionBusy) && (
        <p className="loading" role="status">
          불러오는 중…
        </p>
      )}
      {children}
    </>
  );
}
