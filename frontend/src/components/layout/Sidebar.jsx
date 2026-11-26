import React from 'react';
import { ArrowLeft, LogOut } from 'lucide-react';
import { NAV_ITEMS } from '../../constants/navigation.js';
import { useAuth } from '../../context/AuthContext.jsx';
import { usePlanner } from '../../context/PlannerContext.jsx';
import { Brand } from '../common/Brand.jsx';

export function Sidebar() {
  const { home, isDemo } = useAuth();
  const { page, goToPage, handleSignOut } = usePlanner();

  return (
    <aside className="sidebar">
      <Brand />
      <nav aria-label="메인 메뉴">
        {NAV_ITEMS.map(({ key, label, Icon }) => (
          <button
            key={key}
            className={page === key ? 'selected' : ''}
            aria-current={page === key ? 'page' : undefined}
            onClick={() => goToPage(key)}
          >
            <Icon size={20} />
            <span>{label}</span>
          </button>
        ))}
      </nav>
      <div className="account">
        <div className="avatar">{home.nickname?.slice(0, 1) || 'M'}</div>
        <div>
          <strong>{home.nickname}</strong>
          <small>{isDemo ? '예시 모드' : '내 계정'}</small>
        </div>
        <button
          className="icon"
          aria-label={isDemo ? '예시 모드 종료' : '로그아웃'}
          onClick={handleSignOut}
        >
          {isDemo ? <ArrowLeft size={18} /> : <LogOut size={18} />}
        </button>
      </div>
    </aside>
  );
}
