import React from 'react';
import { koreanDate } from '../../utils/date.js';
import { AttachmentList } from '../planner/AttachmentList.jsx';

export function DiaryForm({ item = {}, date, onSubmit, onValidationError, footer }) {
  function handleSubmit(event) {
    event.preventDefault();
    const fields = new FormData(event.currentTarget);
    const content = fields.get('content').trim();

    if (!content) {
      onValidationError('일기 내용을 입력해 주세요.');
      return;
    }

    onSubmit({ diaryDate: date, content });
  }

  return (
    <form onSubmit={handleSubmit}>
      <p className="muted">{koreanDate(date)}</p>
      <label>
        내용
        <textarea
          name="content"
          rows={7}
          defaultValue={item.content || ''}
          required
        />
      </label>
      <AttachmentList files={item.mediaFiles} />
      {footer}
    </form>
  );
}
