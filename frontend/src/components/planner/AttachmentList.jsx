import React from 'react';
import { ArrowRight } from 'lucide-react';

export function AttachmentList({ files }) {
  if (!files?.length) return null;

  return (
    <section className="attachments">
      <h3>첨부파일</h3>
      {files.map((file) => (
        <a
          key={file.mediaId}
          href={`/api${file.fileUrl}`}
          target="_blank"
          rel="noreferrer"
        >
          {file.originalFilename || '첨부파일 열기'} <ArrowRight size={14} />
        </a>
      ))}
    </section>
  );
}
