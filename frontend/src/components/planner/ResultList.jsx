import React from 'react';
import { BookOpen, CalendarDays, ChevronRight } from 'lucide-react';
import { koreanDate } from '../../utils/date.js';

export function ResultList({ items, onOpen }) {
  if (!items.length) {
    return <p className="empty">표시할 기록이 없어요.</p>;
  }

  return (
    <div className="results">
      {items.map((item) => (
        <button key={item.type + item.id} onClick={() => onOpen(item)}>
          <span className={`section-icon ${item.type === 'DIARY' ? 'peach' : 'purple'}`}>
            {item.type === 'DIARY' ? <BookOpen size={18} /> : <CalendarDays size={18} />}
          </span>
          <span>
            <strong>
              {item.title || (item.dateTime ? koreanDate(item.dateTime) : '일기')}
            </strong>
            {item.content && <small>{item.content}</small>}
          </span>
          <ChevronRight size={18} />
        </button>
      ))}
    </div>
  );
}
