
import React, { useEffect, useState } from 'react';
import { Avatar, AvatarFallback, AvatarImage } from './ui/avatar';
import { Button } from './ui/button';
import { Settings } from 'lucide-react';
import { Api, ProfileResponseDto, FollowDto } from '@/api/api'; // Api 클래스, ProfileResponseDto, FollowDto 타입 임포트
import { useSelector } from 'react-redux';
import { RootState } from '@/app/store';

export const ProfileHeader = () => {
  const [profile, setProfile] = useState<ProfileResponseDto | null>(null);
  const [followersCount, setFollowersCount] = useState(0);
  const [followingsCount, setFollowingsCount] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const api = new Api(); // Api 클래스의 인스턴스 생성

  // 현재 로그인한 사용자 ID 가져오기 (Redux 스토어에서)
  const currentUserId = useSelector((state: RootState) => state.auth.user?.id); // authSlice에 user.id가 있다고 가정

  useEffect(() => {
    const fetchProfileData = async () => {
      if (!currentUserId) {
        setError('사용자 ID를 찾을 수 없습니다.');
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        // 프로필 정보 가져오기
        const profileResponse = await api.api.getProfile(currentUserId);
        setProfile(profileResponse.data);

        // 팔로워 수 가져오기
        const followersResponse = await api.api.getFollowers(currentUserId);
        setFollowersCount(followersResponse.data.length);

        // 팔로잉 수 가져오기
        const followingsResponse = await api.api.getFollowings(currentUserId);
        setFollowingsCount(followingsResponse.data.length);

      } catch (err) {
        console.error('Failed to fetch profile data:', err);
        setError('프로필 데이터를 불러오는 데 실패했습니다.');
      } finally {
        setLoading(false);
      }
    };

    fetchProfileData();
  }, [currentUserId]);

  if (loading) {
    return <div className="flex flex-col md:flex-row gap-8 mb-8 text-center">프로필 정보를 불러오는 중...</div>;
  }

  if (error) {
    return <div className="flex flex-col md:flex-row gap-8 mb-8 text-center text-red-500">{error}</div>;
  }

  if (!profile) {
    return <div className="flex flex-col md:flex-row gap-8 mb-8 text-center">프로필을 찾을 수 없습니다.</div>;
  }

  return (
    <div className="flex flex-col md:flex-row gap-8 mb-8">
      {/* Profile Picture */}
      <div className="flex justify-center md:justify-start">
        <Avatar className="w-32 h-32 md:w-40 md:h-40">
          <AvatarImage src={profile.profileImageUrl || "https://via.placeholder.com/300"} alt="프로필" />
          <AvatarFallback className="text-2xl bg-instagram-gray">UN</AvatarFallback>
        </Avatar>
      </div>

      {/* Profile Info */}
      <div className="flex-1 text-center md:text-left">
        {/* Username and Actions */}
        <div className="flex flex-col md:flex-row items-center gap-4 mb-4">
          <h1 className="text-2xl font-light">{profile.username || '사용자'}</h1> {/* username 필드가 ProfileResponseDto에 없으므로 임시로 '사용자' */}
          <div className="flex gap-2">
            <Button variant="secondary" className="bg-instagram-gray text-instagram-text hover:bg-instagram-border">
              프로필 편집
            </Button>
            <Button variant="secondary" size="icon" className="bg-instagram-gray text-instagram-text hover:bg-instagram-border">
              <Settings size={16} />
            </Button>
          </div>
        </div>

        {/* Stats */}
        <div className="flex justify-center md:justify-start gap-8 mb-4">
          <div className="text-center">
            <div className="font-semibold">게시물 수 (API 없음)</div> {/* 게시물 수는 별도 API 필요 */}
            <div className="text-instagram-muted text-sm">게시물</div>
          </div>
          <div className="text-center">
            <div className="font-semibold">{followersCount}</div>
            <div className="text-instagram-muted text-sm">팔로워</div>
          </div>
          <div className="text-center">
            <div className="font-semibold">{followingsCount}</div>
            <div className="text-instagram-muted text-sm">팔로잉</div>
          </div>
        </div>

        {/* Bio */}
        <div className="text-sm">
          <div className="font-medium mb-1">{profile.username || '사용자'}</div> {/* username 필드가 ProfileResponseDto에 없으므로 임시로 '사용자' */}
          <div className="text-instagram-muted">
            {profile.bio || '소개 없음'}
          </div>
        </div>
      </div>
    </div>
  );
};

