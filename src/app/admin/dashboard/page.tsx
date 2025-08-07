"use client";

import React, { useState, useEffect } from 'react';
import { AdminDashboardResponse, getAdminDashboardStats } from '@/lib/apis/admin.api'; // API 파일에서 필요한 항목을 import합니다.

const formatDate = (isoString: string): string => {
  const date = new Date(isoString);
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  });
};

const AdminDashboardPage = () => {
  const [dashboardData, setDashboardData] = useState<AdminDashboardResponse | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const data = await getAdminDashboardStats();
        setDashboardData(data);
      } catch (e) {
        console.error("Failed to fetch dashboard data:", e);
        setError("대시보드 데이터를 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    fetchDashboardData();
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gray-100 p-4 font-sans">
        <div className="text-gray-600 text-xl">데이터를 불러오는 중...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gray-100 p-4 font-sans text-red-500 text-xl">
        {error}
      </div>
    );
  }

  if (!dashboardData) {
    return null;
  }

  return (
    <div className="min-h-screen bg-gray-100 p-4 md:p-8 font-sans">
      <div className="max-w-6xl mx-auto bg-white rounded-xl shadow-lg p-6 md:p-10">
        <h1 className="text-3xl md:text-4xl font-bold text-gray-800 mb-6 border-b pb-4">
          관리자 대시보드
        </h1>

        {/* 총 상점 수 섹션 */}
        <div className="bg-blue-500 text-white rounded-lg p-6 mb-8 shadow-md">
          <p className="text-lg font-medium">총 상점 수</p>
          <p className="text-5xl font-extrabold mt-2">{dashboardData.totalStoreCount}</p>
        </div>

        {/* 이벤트별 방문 통계 섹션 */}
        <section className="mb-8">
          <h2 className="text-2xl font-semibold text-gray-700 mb-4">이벤트별 방문 통계</h2>
          <div className="overflow-x-auto bg-gray-50 rounded-lg shadow-inner">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-100">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">상점명</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">이벤트명</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">기간</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">방문객 수</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {dashboardData.eventStats.map((stat, index) => (
                  <tr key={index} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{stat.storeName}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">{stat.eventTitle}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                      {formatDate(stat.eventStart)} ~ {formatDate(stat.eventEnd)}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600 font-bold">{stat.visitorCount.toLocaleString()}명</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>

        {/* 월별 방문 통계 섹션 */}
        <section>
          <h2 className="text-2xl font-semibold text-gray-700 mb-4">월별 이용객 통계</h2>
          <div className="overflow-x-auto bg-gray-50 rounded-lg shadow-inner">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-100">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">상점명</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">년도</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">월</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">방문객 수</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {dashboardData.monthlyStats.map((stat, index) => (
                  <tr key={index} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{stat.storeName}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">{stat.year}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">{stat.month}월</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600 font-bold">{stat.visitorCount.toLocaleString()}명</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
      </div>
    </div>
  );
};

export default AdminDashboardPage;
