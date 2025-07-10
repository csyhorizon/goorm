import React from 'react';
import { Sidebar } from '@/components/Sidebar';
import { RightPanel } from '@/components/home/RightPanel';

const NotificationsPage: React.FC = () => {
  return (
    <div className="min-h-screen bg-gray-50 flex">
      {/* Left Sidebar */}
      <Sidebar />
      
      {/* Main Content */}
      <div className="flex-1 flex justify-center">
        <div className="max-w-2xl w-full p-6">
          <h1 className="text-2xl font-bold mb-6">알림</h1>
          <div className="bg-white rounded-lg shadow">
            <div className="p-6 text-center text-gray-500">
              <div className="text-4xl mb-4">🔔</div>
              <h3 className="text-lg font-medium mb-2">새로운 알림이 없습니다</h3>
              <p className="text-sm">새로운 활동이 있을 때 여기에 표시됩니다.</p>
            </div>
          </div>
        </div>
      </div>
      
      {/* Right Panel */}
      <RightPanel />
    </div>
  );
};

export default NotificationsPage; 