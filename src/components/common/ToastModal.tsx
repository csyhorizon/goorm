'use client';

import { useRouter } from 'next/navigation';

interface ToastModalProps {
  message: string;
  redirectPath?: string;
  onClose: () => void;
}

export default function ToastModal({ message, redirectPath, onClose }: ToastModalProps) {
  const router = useRouter();

  const handleClick = () => {
    if (redirectPath) {
      router.push(redirectPath);
    }
    onClose();
  };

  return (
    <div
      onClick={handleClick}
      style={{
        position: 'fixed',
        top: '20px',
        left: '50%',
        transform: 'translateX(-50%)',
        padding: '12px 20px',
        backgroundColor: 'rgba(0, 0, 0, 0.7)',
        color: 'white',
        borderRadius: '20px',
        boxShadow: '0 4px 12px rgba(0,0,0,0.15)',
        zIndex: 9999,
        cursor: 'pointer',
        animation: 'fadeInOut 4s forwards',
      }}
    >
      {message}
    </div>
  );
}