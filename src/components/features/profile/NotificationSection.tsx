'use client';

const ToggleSwitch = ({ label, enabled, onToggle }: { label: string, enabled: boolean, onToggle: () => void }) => (
    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '16px 0' }}>
        <span style={{ color: '#333' }}>{label}</span>
        <label style={{ position: 'relative', display: 'inline-block', width: '50px', height: '28px' }}>
            <input type="checkbox" checked={enabled} onChange={onToggle} style={{ opacity: 0, width: 0, height: 0 }} />
            <span style={{ position: 'absolute', cursor: 'pointer', top: 0, left: 0, right: 0, bottom: 0, backgroundColor: enabled ? '#007bff' : '#ccc', transition: '.4s', borderRadius: '28px' }}>
                <span style={{ position: 'absolute', height: '20px', width: '20px', left: '4px', bottom: '4px', backgroundColor: 'white', transition: '.4s', borderRadius: '50%', transform: enabled ? 'translateX(22px)' : 'translateX(0)' }}></span>
            </span>
        </label>
    </div>
);

interface NotificationSettings {
  pushEnabled: boolean;
  emailEnabled: boolean;
}

interface NotificationSectionProps {
  settings: NotificationSettings | null;
  onSettingChange: (type: 'push' | 'email', value: boolean) => void;
}

export default function NotificationSection({ settings, onSettingChange }: NotificationSectionProps) {
  if (!settings) return null;

  return (
    <section style={{ marginBottom: '40px' }}>
      <h2 style={{ fontSize: '1.2rem', borderBottom: '1px solid #eee', paddingBottom: '10px', color: 'black' }}>알림 설정</h2>
      <ToggleSwitch 
        label="푸시 알림" 
        enabled={settings.pushEnabled} 
        onToggle={() => onSettingChange('push', !settings.pushEnabled)} 
      />
      <ToggleSwitch 
        label="이메일 알림" 
        enabled={settings.emailEnabled} 
        onToggle={() => onSettingChange('email', !settings.emailEnabled)} 
      />
    </section>
  );
}