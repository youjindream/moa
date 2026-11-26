import React, { useEffect, useState } from 'react';
import { X } from 'lucide-react';

export function ModalDialog({ title, titleId = 'dialog-title', busy = false, onClose, children }) {
  const [dialog, setDialog] = useState(null);

  useEffect(() => {
    if (dialog && !dialog.open) {
      dialog.showModal();
    }
  }, [dialog]);

  return (
    <dialog
      ref={setDialog}
      onCancel={(event) => {
        event.preventDefault();
        if (!busy) onClose();
      }}
      aria-labelledby={titleId}
    >
      <header className="dialog-header">
        <h2 id={titleId}>{title}</h2>
        <button
          type="button"
          className="icon"
          aria-label="닫기"
          disabled={busy}
          onClick={onClose}
        >
          <X size={22} />
        </button>
      </header>
      {children}
    </dialog>
  );
}
