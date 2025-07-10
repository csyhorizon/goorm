import React from 'react';

export default function DMListSidebar({ onSelectRoom, selectedRoomId }) {
  // 임시 데이터 예시
  const rooms = [
    { id: '1', username: '홍길동', profileImage: '/profile1.png', lastMessage: '뭐하고 있나요?', unreadCount: 2 },
    { id: '2', username: '김철수', profileImage: '/profile2.png', lastMessage: '뭐하고 있나요?', unreadCount: 0 },
  ];

  return (
    <aside className="w-80 border-r border-gray-800 flex flex-col">
      <div className="p-4 text-xl font-bold">메시지</div>
      <ul className="flex-1 overflow-y-auto">
        {rooms.map(room => (
          <li
            key={room.id}
            className={`flex items-center p-3 cursor-pointer hover:bg-gray-900 ${selectedRoomId === room.id ? 'bg-gray-900' : ''}`}
            onClick={() => onSelectRoom(room.id)}
          >
            <img src={room.profileImage} className="w-12 h-12 rounded-full mr-3" />
            <div className="flex-1">
              <div className="font-semibold">{room.username}</div>
              <div className="text-sm text-gray-400 truncate">{room.lastMessage}</div>
            </div>
            {room.unreadCount > 0 && (
              <span className="ml-2 bg-blue-500 text-xs rounded-full px-2 py-0.5">{room.unreadCount}</span>
            )}
          </li>
        ))}
      </ul>
    </aside>
  );
}