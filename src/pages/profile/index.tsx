
import React from 'react';
import { Sidebar } from '../components/Sidebar';
import { ProfileHeader } from '../components/ProfileHeader';
import { ProfileTabs } from '../components/ProfileTabs';

const Profile = () => {
  return (
    <div className="min-h-screen bg-instagram-dark text-instagram-text flex">
      {/* Left Sidebar */}
      <Sidebar />
      
      {/* Main Profile Content */}
      <div className="flex-1 max-w-4xl mx-auto">
        <div className="px-4 py-8">
          <ProfileHeader />
          <ProfileTabs />
        </div>
      </div>
    </div>
  );
};

export default Profile;
