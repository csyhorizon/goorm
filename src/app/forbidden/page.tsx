import Link from 'next/link';

export default function ForbiddenPage() {
    return (
        <div style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            height: '80vh',
            textAlign: 'center',
            padding: '20px',
        }}>
            <h1 style={{
                fontSize: '2rem',
                fontWeight: 'bold',
                marginBottom: '16px',
                color: '#dc3545'
            }}>
                접근 권한이 없습니다.
            </h1>
            <p style={{
                fontSize: '1.1rem',
                color: '#6c757d',
                marginBottom: '32px'
            }}>
                요청하신 페이지에 접근할 수 있는 권한이 없습니다. <br />
            </p>
            <Link href="/" style={{
                padding: '12px 24px',
                backgroundColor: '#007bff',
                color: 'white',
                textDecoration: 'none',
                borderRadius: '8px',
                fontWeight: 'bold',
            }}>
                홈으로 돌아가기
            </Link>
        </div>
    );
}
