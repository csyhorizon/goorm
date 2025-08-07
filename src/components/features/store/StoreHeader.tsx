'use client';

interface StoreHeaderProps {
  name: string;
  category: string;
}

export default function StoreHeader({ name, category }: StoreHeaderProps) {
  return (
    <div style={{
      position: 'relative',
      width: '100%',
      height: '250px',
      backgroundColor: '#2c3e50',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'flex-end',
      padding: '24px',
      boxSizing: 'border-box',
      color: 'white',
    }}>
      <div>
        <p style={{ margin: '0 0 4px 0', color: '#bdc3c7', fontSize: '1rem' }}>{category}</p>
        <h1 style={{ margin: 0, fontSize: '2.5rem', fontWeight: 'bold' }}>{name}</h1>
      </div>
    </div>
  );
}
