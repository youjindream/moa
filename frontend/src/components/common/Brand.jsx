import React from 'react';
import { Aperture } from 'lucide-react';

export function Brand() {
  return (
    <div className="brand">
      <span>
        <Aperture size={24} />
      </span>
      <strong>
        moa<span>.</span>
      </strong>
    </div>
  );
}
