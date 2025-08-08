'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { getMyProfile } from '@/lib/apis/member.api';

export default function FloatingWriteButton() {
    const [userRole, setUserRole] = useState<string | null>(null);
    const [isLoadingRole, setIsLoadingRole] = useState<boolean>(true);

    useEffect(() => {
        const fetchUserRole = async () => {
            try {
                const profileData = await getMyProfile();
                setUserRole(profileData.role);
            } catch (error) {
                console.error("사용자 프로필을 불러오는 데 실패했습니다:", error);
                setUserRole(null);
            } finally {
                setIsLoadingRole(false);
            }
        };
        fetchUserRole();
    }, []);

    if (isLoadingRole || userRole !== 'OWNER') {
        return null;
    }

    return (
        <Link href="/community/create" passHref>
            <button style={{
                position: 'fixed',
                bottom: '80px',
                right: '20px',
                width: '56px',
                height: '56px',
                borderRadius: '50%',
                backgroundColor: '#ff6f0f',
                border: 'none',
                boxShadow: '0 4px 8px rgba(0,0,0,0.2)',
                cursor: 'pointer',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                zIndex: 999,
            }}>
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M12 5V19M5 12H19" stroke="white" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round" />
                </svg>
            </button>
        </Link>
    );
}
