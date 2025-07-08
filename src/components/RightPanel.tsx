
import React, { useEffect, useState } from 'react';
import { Api, UserSearchResultDto } from '@/api/api'; // Api 클래스와 UserSearchResultDto 타입 임포트

export const RightPanel = () => {
  const [suggestedUsers, setSuggestedUsers] = useState<UserSearchResultDto[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const api = new Api(); // Api 클래스의 인스턴스 생성

  useEffect(() => {
    const fetchSuggestedUsers = async () => {
      try {
        setLoading(true);
        // '추천'이라는 키워드로 사용자 검색 (예시)
        // 실제로는 팔로우하지 않은 사용자 목록을 가져오는 API가 더 적절합니다.
        const response = await api.api.searchResult('추천');
        setSuggestedUsers(response.data); // 응답 데이터로 사용자 상태 업데이트
      } catch (err) {
        console.error('Failed to fetch suggested users:', err);
        setError('추천 사용자를 불러오는 데 실패했습니다.');
      } finally {
        setLoading(false);
      }
    };

    fetchSuggestedUsers();
  }, []);

  if (loading) {
    return <div className="w-80 p-6 hidden lg:block text-center">추천 사용자를 불러오는 중...</div>;
  }

  if (error) {
    return <div className="w-80 p-6 hidden lg:block text-center text-red-500">{error}</div>;
  }

  return (
    <div className="w-80 p-6 hidden lg:block">
      {/* User Profile Summary */}
      <div className="flex items-center justify-between mb-6">
        <div className="flex items-center space-x-3">
          <div className="w-14 h-14 rounded-full overflow-hidden">
            <img
              src="https://images.unsplash.com/photo-1649972904349-6e44c42644a7?w=56&h=56&fit=crop&crop=face"
              alt="Your profile"
              className="w-full h-full object-cover"
            />
          </div>
          <div>
            <div className="font-semibold text-instagram-text">ts_jdu</div>
            <div className="text-sm text-instagram-muted">회원님을 위한 추천</div>
          </div>
        </div>
        <button className="text-instagram-blue text-sm font-semibold hover:text-blue-400">
          전환
        </button>
      </div>

      {/* Suggestions */}
      <div>
        <div className="flex items-center justify-between mb-4">
          <span className="text-instagram-muted font-semibold text-sm">회원님을 위한 추천</span>
          <button className="text-instagram-text text-sm hover:text-instagram-muted">
            모두 보기
          </button>
        </div>

        <div className="space-y-3">
          {suggestedUsers.length > 0 ? (
            suggestedUsers.map((user, index) => (
              <div key={index} className="flex items-center justify-between">
                <div className="flex items-center space-x-3">
                  <div className="w-10 h-10 rounded-full overflow-hidden">
                    <img
                      src={user.profileImageUrl || 'https://via.placeholder.com/40'} // 프로필 이미지 URL이 없으면 기본 이미지 사용
                      alt={user.username}
                      className="w-full h-full object-cover"
                    />
                  </div>
                  <div>
                    <div className="font-semibold text-sm text-instagram-text">{user.username}</div>
                    <div className="text-xs text-instagram-muted line-clamp-1">{user.subtitle || '회원님을 위한 추천'}</div>
                  </div>
                </div>
                <button className="text-instagram-blue text-sm font-semibold hover:text-blue-400">
                  팔로우
                </button>
              </div>
            ))
          ) : (
            <div className="text-center text-instagram-muted">추천 사용자가 없습니다.</div>
          )}
        </div>
      </div>

      {/* Footer */}
      <div className="mt-8 text-xs text-instagram-muted space-y-2">
        <div className="space-x-2">
          <span>소개</span>
          <span>도움말</span>
          <span>API</span>
          <span>채용 정보</span>
        </div>
        <div className="space-x-2">
          <span>개인정보처리방침</span>
          <span>약관</span>
          <span>위치</span>
          <span>언어</span>
          <span>Meta Verified</span>
        </div>
        <div>© 2025 UNIQRAM FROM META</div>
      </div>
    </div>
  );
};
