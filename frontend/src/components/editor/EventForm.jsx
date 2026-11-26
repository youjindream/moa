import React from 'react';
import { AttachmentList } from '../planner/AttachmentList.jsx';

export function EventForm({ item = {}, date, id, onSubmit, onValidationError, footer }) {
  function handleSubmit(event) {
    event.preventDefault();
    const fields = new FormData(event.currentTarget);
    const title = fields.get('title').trim();
    const startTime = fields.get('startTime');
    const endTime = fields.get('endTime') || null;

    if (!title || (endTime && endTime < startTime)) {
      onValidationError(
        !title
          ? '일정 제목을 입력해 주세요.'
          : '종료 시간은 시작 시간 이후로 설정해 주세요.',
      );
      return;
    }

    onSubmit({
      title,
      startTime,
      endTime,
      content: fields.get('content'),
      isAllDay: fields.has('isAllDay'),
      isCompleted: fields.has('isCompleted'),
      labelId: item.labelId ?? null,
      recurrenceRule: item.recurrenceRule ?? null,
      alarmMinutesBefore:
        fields.get('alarm') === '' ? null : Number(fields.get('alarm')),
    });
  }

  return (
    <form onSubmit={handleSubmit}>
      <label>
        제목
        <input
          name="title"
          defaultValue={item.title}
          maxLength={200}
          required
          autoFocus
        />
      </label>
      <div className="form-pair">
        <label>
          시작
          <input
            name="startTime"
            type="datetime-local"
            defaultValue={item.startTime?.slice(0, 16) || `${date}T09:00`}
            required
          />
        </label>
        <label>
          종료
          <input
            name="endTime"
            type="datetime-local"
            defaultValue={item.endTime?.slice(0, 16)}
          />
        </label>
      </div>
      <div className="checks">
        <label>
          <input name="isAllDay" type="checkbox" defaultChecked={item.isAllDay} />
          종일
        </label>
        {id && (
          <label>
            <input
              name="isCompleted"
              type="checkbox"
              defaultChecked={item.isCompleted}
            />
            완료
          </label>
        )}
      </div>
      <label>
        알림 (시작 몇 분 전)
        <input
          name="alarm"
          type="number"
          min="0"
          max="2147483647"
          defaultValue={item.alarmMinutesBefore ?? ''}
          placeholder="알림 없음"
        />
      </label>
      <label>
        메모
        <textarea name="content" rows={3} defaultValue={item.content || ''} />
      </label>
      <AttachmentList files={item.mediaFiles} />
      {footer}
    </form>
  );
}
