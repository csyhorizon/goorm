import KakaoMap from "@/components/common/map/KakaoMap";
import NotificationBell from "@/components/layout/NotificationBell";
import { SSEProvider } from "@/contexts/SSEProvider";

export default function Home() {
  return (
    <SSEProvider>
      <header style={{
        position: 'fixed',  
        top: 0,
        left: 0,
        right: 0,
        display: 'flex',
        justifyContent: 'flex-end',
        padding: '10px 20px',
        zIndex: 1000,
        pointerEvents: 'none',
      }}>
        <div style={{ pointerEvents: 'auto' }}>
          <NotificationBell />
        </div>
      </header>

      <div>
        <KakaoMap />
      </div>
    </SSEProvider>
  );
}