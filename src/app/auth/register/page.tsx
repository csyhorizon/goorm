import RegisterForm from '@/components/auth/RegisterForm';
import Link from 'next/link';

export default function RegisterPage() {
  return (
    <>
      <h2 className="text-2xl font-bold text-center text-gray-900 mb-6">회원가입</h2>
      <RegisterForm />
      <p className="mt-6 text-center text-sm text-gray-600">
        이미 계정이 있으신가요?{' '}
        <Link href="/auth/login" className="font-medium text-indigo-600 hover:text-indigo-500">
          로그인하기
        </Link>
      </p>
    </>
  );
}