import React from 'react';
import DMMessageList from './DMMessageList';
import DMMessageInput from './DMMessageInput';

export default function DMChatWindow({ roomId }) {
  if (!roomId) {
    return <div className="flex-1 flex items-center justify-center text-gray-500">대화를 선택하세요</div>;
  }
  return (
    <section className="flex-1 flex flex-col h-full">
      <DMMessageList roomId={roomId} />
      <DMMessageInput roomId={roomId} />
    </section>
  );
}