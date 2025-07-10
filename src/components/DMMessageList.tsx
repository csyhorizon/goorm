import React, { useEffect, useRef, useState } from 'react';

interface Message {
  id: string;
  content: string;
  isMine: boolean;
  timestamp: string;
  isRead: boolean;
}

interface DMMessageListProps {
  roomId: string | null;
}

const dummyMessages: Message[] = [
  { id: 'm1', content: '안녕하세요!', isMine: false, timestamp: '오전 10:00', isRead: true },
  { id: 'm2', content: '네, 반가워요!', isMine: true, timestamp: '오전 10:01', isRead: true },
  { id: 'm3', content: '뭐하고 있나요?', isMine: false, timestamp: '오전 10:02', isRead: true }
];

export default function DMMessageList({ roomId }: DMMessageListProps) {
  const [messages, setMessages] = useState<Message[]>([]);
  const listRef = useRef<HTMLDivElement>(null);

  // roomId가 바뀌면 메시지 불러오기 (여기선 더미 사용)
  useEffect(() => {
    if (roomId) {
      // TODO: 실제 API로 교체
      setMessages(dummyMessages);
    } else {
      setMessages([]);
    }
  }, [roomId]);

  // 새 메시지가 오면 스크롤을 맨 아래로 이동
  useEffect(() => {
    if (listRef.current) {
      listRef.current.scrollTop = listRef.current.scrollHeight;
    }
  }, [messages, roomId]);

  if (!roomId) {
    return <div className="flex-1 flex items-center justify-center text-gray-500">대화를 선택하세요</div>;
  }

  return (
    <div
      ref={listRef}
      className="flex-1 overflow-y-auto flex flex-col p-4 space-y-reverse space-y-2"
      style={{ minHeight: 0 }}
    >
      {messages.map(msg => (
        <div
          key={msg.id}
          className={`flex ${msg.isMine ? 'justify-end' : 'justify-start'} mb-2`}
        >
          <div className={`max-w-xs px-4 py-2 rounded-2xl ${msg.isMine ? 'bg-blue-500 text-white' : 'bg-gray-800 text-white'}`}>
            <div>{msg.content}</div>
            <div className="text-xs text-gray-400 text-right mt-1">
              {msg.timestamp} {msg.isRead && <span>읽음</span>}
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}