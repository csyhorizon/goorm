import React, { useState } from 'react';

export default function DMMessageInput({ roomId }) {
  const [text, setText] = useState('');

  const sendMessage = () => {
    if (!text.trim()) return;
    // TODO: WebSocket 또는 API로 메시지 전송
    setText('');
  };

  return (
    <div className="p-4 border-t border-gray-800 flex">
      <input
        className="flex-1 bg-gray-900 text-white rounded-full px-4 py-2 outline-none"
        value={text}
        onChange={e => setText(e.target.value)}
        onKeyDown={e => e.key === 'Enter' && sendMessage()}
        placeholder="메시지 입력..."
      />
      <button
        className="ml-2 bg-blue-500 text-white px-4 py-2 rounded-full"
        onClick={sendMessage}
      >
        전송
      </button>
    </div>
  );
}