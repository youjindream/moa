import React from 'react';

export function ErrorMessage({ message }) {
  if (!message) return null;
  return (
    <p role="alert" className="error">
      {message}
    </p>
  );
}
