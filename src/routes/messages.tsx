import React, { useState } from 'react';
import DMListSidebar from '../components/DMListSidebar';
import DMChatWindow from '../components/DMChatWindow';

export default function DMPage() {
  const [selectedRoomId, setSelectedRoomId] = useState<string | null>(null);

  return (
    <div className="flex h-screen bg-black text-white">
      <DMListSidebar onSelectRoom={setSelectedRoomId} selectedRoomId={selectedRoomId} />
      <DMChatWindow roomId={selectedRoomId} />
    </div>
  );
}