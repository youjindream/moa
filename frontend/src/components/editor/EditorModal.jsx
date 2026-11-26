import React, { useState } from 'react';
import { useAuth } from '../../context/AuthContext.jsx';
import { usePlanner } from '../../context/PlannerContext.jsx';
import { ErrorMessage } from '../common/ErrorMessage.jsx';
import { ModalDialog } from '../common/ModalDialog.jsx';
import { DiaryForm } from './DiaryForm.jsx';
import { EventForm } from './EventForm.jsx';
import { LabelDetail } from './LabelDetail.jsx';

const EDITOR_NAMES = {
  event: '일정',
  diary: '일기',
  label: '라벨',
};

export function EditorModal() {
  const { api, expireSession } = useAuth();
  const { editor, closeEditor, notifyChanged } = usePlanner();
  const [busy, setBusy] = useState(false);
  const [confirmDelete, setConfirmDelete] = useState(false);
  const [error, setError] = useState('');

  if (!editor) return null;

  const { kind, item = {}, date } = editor;
  const id = kind === 'event' ? item.eventId : kind === 'diary' ? item.diaryId : item.labelId;
  const name = EDITOR_NAMES[kind];
  const title = kind === 'label' ? '라벨' : `${name}${id ? ' 편집' : ' 추가'}`;

  async function handleSave(body) {
    if (busy) return;
    setBusy(true);
    setError('');
    try {
      if (kind === 'event') {
        await api.saveEvent(id, body);
      } else {
        await api.saveDiary(id, body);
      }
      notifyChanged('저장했어요.');
    } catch (failure) {
      if (failure.status === 401) {
        closeEditor();
        expireSession();
      } else {
        setError(failure.message);
      }
    } finally {
      setBusy(false);
    }
  }

  async function handleDelete() {
    if (busy) return;
    setBusy(true);
    setError('');
    try {
      if (kind === 'event') {
        await api.deleteEvent(id);
      } else {
        await api.deleteDiary(id);
      }
      notifyChanged('삭제했어요.');
    } catch (failure) {
      if (failure.status === 401) {
        closeEditor();
        expireSession();
      } else {
        setError(failure.message);
      }
    } finally {
      setBusy(false);
    }
  }

  const formFooter = (
    <>
      <ErrorMessage message={error} />
      {confirmDelete ? (
        <div className="dialog-actions">
          <p>{name}를 삭제할까요?</p>
          <button
            type="button"
            className="secondary"
            disabled={busy}
            onClick={() => setConfirmDelete(false)}
          >
            취소
          </button>
          <button
            type="button"
            className="danger"
            disabled={busy}
            onClick={handleDelete}
          >
            삭제
          </button>
        </div>
      ) : (
        <div className="dialog-actions">
          {id && (
            <button
              type="button"
              className="link remove"
              disabled={busy}
              onClick={() => setConfirmDelete(true)}
            >
              삭제
            </button>
          )}
          <button className="primary" disabled={busy}>
            {busy ? '처리 중…' : '저장'}
          </button>
        </div>
      )}
    </>
  );

  return (
    <ModalDialog
      title={title}
      titleId="editor-title"
      busy={busy}
      onClose={closeEditor}
    >
      {kind === 'label' && <LabelDetail item={item} />}
      {kind === 'event' && (
        <EventForm
          item={item}
          date={date}
          id={id}
          onSubmit={handleSave}
          onValidationError={setError}
          footer={formFooter}
        />
      )}
      {kind === 'diary' && (
        <DiaryForm
          item={item}
          date={date}
          onSubmit={handleSave}
          onValidationError={setError}
          footer={formFooter}
        />
      )}
    </ModalDialog>
  );
}
