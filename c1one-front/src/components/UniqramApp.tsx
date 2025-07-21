
import React from 'react';
import { Sidebar } from './Sidebar';
import { MainFeed } from './home/MainFeed';
import { RightPanel } from './home/RightPanel';

const UniqramApp = () => {
  return (
    <div className="min-h-screen bg-instagram-dark text-instagram-text flex">
      {/* Left Sidebar */}
      <Sidebar />
      
      {/* Main Content Area */}
      <div className="flex-1 flex">
        {/* Main Feed */}
        <MainFeed />
        
        {/* Right Panel */}
        <RightPanel />
      </div>
    </div>
  );
};

export default UniqramApp;
