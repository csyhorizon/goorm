import React, { useState } from 'react';
import { Api } from '@/api/api';

const ApiTest = () => {
  const [status, setStatus] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');

  const testApiConnection = async () => {
    setLoading(true);
    setError('');
    setStatus('');

    try {
      const api = new Api();
      
      // Spring 서버 연결 테스트
      console.log('🔍 Spring 서버 연결 테스트 시작...');
      
      // 추천 게시물 API 테스트
      const response = await api.posts.getRecommendedPosts();
      
      setStatus(`✅ 연결 성공! 게시물 ${response.data?.length || 0}개 로드됨`);
      console.log('✅ API 연결 성공:', response.data);
      
    } catch (err: any) {
      const errorMessage = err.response?.status 
        ? `❌ 연결 실패 (${err.response.status}): ${err.response.data?.message || err.message}`
        : `❌ 연결 실패: ${err.message}`;
      
      setError(errorMessage);
      console.error('❌ API 연결 실패:', err);
      
      if (err.code === 'ECONNABORTED') {
        setError('⏰ 타임아웃 - Spring 서버가 실행 중인지 확인하세요 (포트 8080)');
      } else if (err.code === 'ERR_NETWORK') {
        setError('🌐 네트워크 에러 - Spring 서버가 실행 중인지 확인하세요 (포트 8080)');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 bg-instagram-gray rounded-lg border border-instagram-border">
      <h3 className="text-lg font-semibold text-instagram-text mb-4">
        🔗 Spring 서버 연결 테스트
      </h3>
      
      <div className="space-y-4">
        <button
          onClick={testApiConnection}
          disabled={loading}
          className={`px-4 py-2 rounded-lg font-medium transition-colors ${
            loading
              ? 'bg-instagram-border text-instagram-muted cursor-not-allowed'
              : 'bg-instagram-blue text-white hover:bg-blue-600'
          }`}
        >
          {loading ? '테스트 중...' : 'API 연결 테스트'}
        </button>

        {status && (
          <div className="p-3 bg-green-900/20 border border-green-500/30 rounded-lg">
            <p className="text-green-400 text-sm">{status}</p>
          </div>
        )}

        {error && (
          <div className="p-3 bg-red-900/20 border border-red-500/30 rounded-lg">
            <p className="text-red-400 text-sm">{error}</p>
          </div>
        )}

        <div className="text-xs text-instagram-muted space-y-1">
          <p>• React 서버: http://localhost:8081</p>
          <p>• Spring 서버: http://localhost:8080</p>
          <p>• API 프록시: /api → http://localhost:8080</p>
        </div>
      </div>
    </div>
  );
};

export default ApiTest; 