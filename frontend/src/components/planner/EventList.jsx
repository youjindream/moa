import React from 'react';
import { Check, Circle } from 'lucide-react';
import { eventTime } from '../../utils/date.js';

export function EventList({ events = [], onOpen, onToggle }) {
  if (!events.length) {
    return <p className="empty">등록된 일정이 없어요.</p>;
  }

  return (
    <div className="event-list">
      {events.map((event) => (
        <div className="event-row" key={event.eventId}>
          {onToggle && (
            <button
              className={`icon completion ${event.isCompleted ? 'done' : ''}`}
              aria-label={event.isCompleted ? '완료 취소' : '완료 표시'}
              onClick={() => onToggle(event.eventId)}
            >
              {event.isCompleted ? <Check size={17} /> : <Circle size={17} />}
            </button>
          )}
          <button className="event-name" onClick={() => onOpen(event.eventId)}>
            <span
              className="dot"
              style={{ background: event.labelColor || '#8975e8' }}
            />
            <span className={event.isCompleted ? 'strike' : ''}>{event.title}</span>
            <time>{eventTime(event)}</time>
          </button>
        </div>
      ))}
    </div>
  );
}
