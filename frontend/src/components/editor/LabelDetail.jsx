import React from 'react';

export function LabelDetail({ item = {} }) {
  return (
    <div className="label-detail">
      <span className="dot" style={{ background: item.colorCode }} />
      {item.title}
    </div>
  );
}
