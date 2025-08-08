import { cookies } from 'next/headers';
import { createServerApi } from '@/lib/apis/serverClient';
import { EventDetailResponse } from '@/lib/apis/event.api';

const createDateFromInput = (dateInput: any): Date | null => {
    if (Array.isArray(dateInput) && dateInput.length >= 3) {
        const [year, month, day, hours = 0, minutes = 0, seconds = 0] = dateInput;
        return new Date(year, month - 1, day, hours, minutes, seconds);
    }
    if (typeof dateInput === 'string') {
        const date = new Date(dateInput.replace(" ", "T"));
        if (!isNaN(date.getTime())) {
            return date;
        }
    }
    return null;
};

const formatDate = (dateInput: any) => {
    const date = createDateFromInput(dateInput);
    if (!date) return '미정';

    return date.toLocaleString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
    });
};

const getDiscountText = (event: EventDetailResponse) => {
    if (event.eventCategory === 'DISCOUNT_PERCENTAGE' && event.discountRate > 0) {
        return `${event.discountRate}% 할인`;
    }
    if (event.eventCategory === 'DISCOUNT_AMOUNT' && event.discountAmount > 0) {
        return `${event.discountAmount.toLocaleString()}원 할인`;
    }
    return "할인 정보 없음";
};


export default async function EventDetailPage({ params }: { params: Promise<{ id: string }> }) {
    const resolvedParams = await params;
    const eventId = parseInt(resolvedParams.id, 10);
    let eventData: EventDetailResponse | null = null;
    let error: string | null = null;

    const cookieStore = cookies();
    const serverApi = createServerApi(await cookieStore);

    try {
        const response = await serverApi.get(`/v1/events/${eventId}`);
        eventData = response.data;
    } catch (e) {
        console.error("이벤트 상세 정보를 불러오는 데 실패했습니다:", e);
        error = "이벤트 정보를 찾을 수 없습니다.";
    }

    if (error || !eventData) {
        return (
            <div style={{ padding: '40px', textAlign: 'center' }}>
                <h1>오류</h1>
                <p>{error || "이벤트 정보를 불러올 수 없습니다."}</p>
            </div>
        );
    }

    const discountText = getDiscountText(eventData);

    return (
        <div style={{ maxWidth: '800px', margin: '40px auto', padding: '20px' }}>
            <header style={{ borderBottom: '2px solid #eee', paddingBottom: '20px', marginBottom: '20px' }}>
                <span style={{
                    backgroundColor: '#e85757',
                    color: 'white',
                    padding: '4px 12px',
                    borderRadius: '16px',
                    fontSize: '1rem',
                    fontWeight: 'bold'
                }}>
                    {discountText}
                </span>
                <h1 style={{ fontSize: '2.5rem', margin: '10px 0 5px 0' }}>{eventData.title}</h1>
                <p style={{ margin: 0, color: '#555', fontSize: '1.1rem' }}>
                    <strong>기간:</strong> {formatDate(eventData.startTime)} ~ {formatDate(eventData.endTime)}
                </p>
            </header>
            <main>
                <p style={{ fontSize: '1.1rem', lineHeight: '1.7', whiteSpace: 'pre-wrap' }}>
                    {eventData.description}
                </p>
            </main>
        </div>
    );
}
