'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import ProfileSection from './ProfileSection';
import NotificationSection from './NotificationSection';
import AccountSection from './AccountSection';

interface User {
  name: string;
  email: string;
  avatarUrl: string;
}
interface NotificationSettings {
  pushEnabled: boolean;
  emailEnabled: boolean;
}

export default function SettingsPage() {
  const [user, setUser] = useState<User | null>(null);
  const [settings, setSettings] = useState<NotificationSettings | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    setIsLoading(true);
    console.log('Fetching initial data...');
    setTimeout(() => {
      setUser({
        name: 'test',
        email: 'test@example.com',
        avatarUrl: 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBwgHBgkIBwgKCgkLDRYPDQwMDRsUFRAWIB0iIiAdHx8kKDQsJCYxJx8fLT0tMTU3Ojo6Iys/RD84QzQ5OjcBCgoKDQwNGg8PGjclHyU3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3N//AABEIAJQAlAMBIgACEQEDEQH/xAAcAAAABwEBAAAAAAAAAAAAAAAAAQIDBAYHBQj/xAA/EAABAwMCAwUGBQMCBAcAAAABAgMEAAURBiEHEjETIkFRYRQyUnGBkTNCobHBI2JyFkMVJCXRCDRTY3Oi8P/EABkBAAMBAQEAAAAAAAAAAAAAAAACBAMFAf/EACQRAAMAAgIDAAICAwAAAAAAAAABAgMREiEEMUEiURNxMkKB/9oADAMBAAIRAxEAPwDcDUR/8VVAvrAJUvAAySfAVn+oOMWl7VIXHZEi5PIUUrVGGEAj+4kA/TNAEjiBcrw/Mg6Y07JMSTOQt6TLxuywnAOPHJJ8PuKrsbhLp5OVz3p0x9Rytxb2OY+J2H801A4m6bvOqmZjrT1vWYhipVIAIyVhXvA7Dbqa0NKgoAjBB8RUXk5bmtI5/l5sk1qekUpzhXpNbZSmJIbV4LElWR98j9Kz262qdww1VCuMV1UiE4pXKsjdSNudCvXHQ1vNcPV2mYuqbWIMxam+VwONuIAJSRt+xrLFnrf5vowxeTSrVvaOy2pLiEuIOUrSFD5Go91ZRItkxlxntkOMLSWsZ5wUnapLaEttpbQMJQkJA8gKUN+lYfdomXT2iqcPNJp0rZlNPFp2Y+vtHXUowQMDCMnfAx9yatWBR75wRRUXTp7Z7dO3uhmXGYmRXYstpLrDqSlxChkKBqn8MtNXHSM6+NPqzDceR7KrIPOkBWSR4HBSPmDV2puQ6GGHXlbpaQpZHyGaeMlSuKGx5blcZ+ku6asslhgpfvFwZjc2eVtSsrV8kjc1Qrnx20+hZRCt1wkBJ99QQ2FfLcn7ioOltHxtQoGptVIM2VP/AKzLC1Hs2WjuhOB12OfKrezpmwsD+jZLajzxFRv+lWPyZnp9s6D8yY/F9sZ0VxNsWqJyITfbQpqvcZk4w5t0SodT6bVoI6V524q6UZsKouodPtCIG3QHUMjCW19UrA6DfbHTpWq3XiLYLLDiu3W4pbffZQ6I7aStwBSc7pHT64qiLVzyRVjyLJPJFnPvH51Jj/hCuBpTVlg1a06uyye1WzjtW1oKFoz0JB8NuortOLU2spScCmHJVCofbL+KhQBV+Idtm3nT8i0WuYmO8+pKXXF5x2f5ht51VtN8MrDaGkKmMpuUrGVOSE5QD5JR0x88mrXf7zb7IlUi6SkMIUshHN7yz5JHUmmLPfrbeQsQHypxABW04gtuJB6EpUAcetc7Jlyvf6OVlzZq3+iJddHaeukRUeTaoqMpwlxlpLa0eoIFcrTU0aVtc616hnIQ1a1j2eQ6cdqwsdzHmQQpOB5VcHFobaU44oBCUlSiegArDm0zOKmtFhxxbNqi5Ix/tNZ2A/uVgf8A4V5iTuWqfQuBPJLVv8UW5fFZiXMVG0/Y59yUPEDl288DJA+ddSPrtTBR/qGxXG0tqOPaHEc7STnHeUPdFWW0WuDZ4SIdsjIjsIGyU+PqT1J+dTFAKSUqAKVDBBGQRS1WP0pErJh3pT1/ZQNf8RW9PBuHaUNSZzrYc7RRy22lXQ7e8T5VVItn4kaqAkvz34MdW6e1fLCSD5ITv9xXVlaXNh4mxLk1Z3JNofIKBFZK0sOEYyUjoAe9npvWrVrVzilcF7NqyRhlcF7+mSu6U1vpuOu5W/UZmqYSVuRlOLUFJAycJVkK/SrbD1tHlWK2zI8J+XOntFSIMVOVApUUqJJ91IUCMn0q2EA7HcHqPOqnwxtDdp0jCyn/AJl5KlPL3ye+ogb9AAeg8ST40jyK53S7QjyTccrXaIEqXxIlkrhW60wGj7qHXedz6nOP0rhXi78TbbFf9vtsWTGU2UrUyyHAARgnuHPjWs0R2G1LOZL/AFQs+Ql/qii8P9fW2+tx7Upow5zbYQ20TlLgSMd0+e3Sr0KxHjFb2bFqeBdbXiPIkJLyuTbDiVDv49c/pW2NL7RtDmMFaQrHlkZr3NEpK5+h5ESkrn6VjVaZV/Q/p22oa5XABPlvJ5kR09QkDxcOxx4bHbapNg0bY7E0nsIaH5GO9KkpDjivqRt8hXdaabaCg0hKApRWoJHVROSfmTS81m8j1xkzeWlPGekZXdXRobizbbpGAZgXIcshtAwnB7qtvQ8qvnW59n2p5849KwPj8WyixgH+oO3OP7f6f8/zW7Wt/wD6bELxIcUyhSs+ZArpYW3jTZ1/Hp1jTY97P/dQpzt0eZ+1FWpsY3p5Y1jr+732YO0hWpfs0BpW6UqB3Xjz2zn1HlV7dhx3pLMpxpJkM57NzooAjBGfEeh9PKsi0DfI2hr3edP6hWphIkd18pJ7w23x0BGCDVm1FxVsVuYUm1rNxlEd1KAQ2n/JR/ioM8ZKydHN8jHlvLpLo6/EW+xLLpaamS4Q/MYcjsISe8pSkkZ9AM5zWecErrFt10n2yYexfmhssFYxlSebKfqFZHypGmdP3XXl4/1BqhahbW+93u6lwDfkQD0R5n5+O4mXuKzxH1GY+nYLEeNDwl+7lJBVjoEgEZ6bZ3+Xi8xMw4b/ALHmImHjf/X+jYgDjONqIGqtC0PCYbT7XcrzNeH+67cHUn6BJGBVjhxkxI6WG1urQnoXXVOK+qlEk1HSlemc+lK9PY9RLWltBWtSUpSMlSjgAUqmJsSPOjqjzGUPsLxzNuDKVYOdxSf2KtfTnDVGn1SfZ03qAXgcBHtCc5+9RdUaotuk7QiS6lKivIjR2jjtT12PgPM1zdQ8NNPXdlXs0VNvk/ldjjlGfVPQj7Gqdw20w8NTz4OoYBlM21HK0XwVNtqKsjlB23G9VTGJre/XwrjHhpck/XwuXDvWM7VqZjkq2JjMMkdm82olKify7+Iqy3mY/AtkiVEhOTH0Jy3Hb6uK6AZ8vOpiEJbSEoSlKU9AkYApVYVUutpdE9VLvaXX6PPspT9w1c3O4iiXBi82yDHUUkA7Npx0T59TW+x5DMphEiO4hxlxPMhaDkKB8RVb4muw2tEXMzkpUkt8rQUN+1JwnHqDv9DXN4MOPL0SgPElCZLiWs+Cdjt6ZJrfI/5MfP8ARTlf8uJX60TtXtawZd9s0tJjvshIC4Lzac580qPX5ZFUp/WHElo9muy8ivNMFR/mtho8+tJGZSu5M48hStOUzEYmjtW6zuhuGpFORm0pPKuQkDzISlvwGevp51fp2p7vom5xjf5SrrYJSg37WppKXoq/JXKAFJ2J6Z6+W9wwT51yNWWxq8acuEF4J/qMKUgqHuqAyk/QitZ8iua66No8uua66LU062+y28w6hbTiQpCwdlA9CKFYdw44ps6f0yi13QrcUw6oMnGcNnBA39Sr6YoqvOmaLxIsulZNseu2qIuVR04Q6yeR1Z/KgEe9nwBzVZ0nw+saWkT5tkLa1YWyxJkl0pSRtzpwE59N6f49PmG1poukqiieXXU427gT/BVV2bWh5tLragptaQpJHQg9Kl8m6lLRF5mSplKfpRuMV0ctWjixFwgzXUxlcu2G+UlQH2A+tdzQlqas2lLbFaAClMpeeI/MtQyT+uPpXI4vWsXPSJUlQS9GfQ40n/1VHKeQeZPNsPMVXtB8TYEW2MWrURcjORUhpuRylQUkbAKAGQR0rBS7w/iTqKvB+P77NYoVyomo7JMSDEu0J0E/leT/AN6g6o1QxabbmA6xKuL7iWYrCHArmcUcDOPAdawWOm9aJljt1x0WKjqDZ4TkCAhiRJclSD3nnnDnnWepA8B5AdBU6la0xGkmChQoq8PNh0R3o6KgDK+IGlNX6juCmm3WpFtijmjha0oU4ojfIAxzeA9B86tmhblaFW1FntrT0ORbxyOwpIw62c7k+YJOcjzq0Yqj8TLa5GitaptJDN0tikqUsf7rWd0q8x/GapVrIuDK5yfypY66/ReaI1CslybvFoh3JnZEllLnL8JPUfQ5FRNU6gRpu3pnPwpUljn5XDHSD2Q+JXpWCl8uP0nUVy4r2Q73oy2XKO72Zkx5ZSrsn0y3e4rwOObB3rKNXR9bachtQbreHXLdJJaStLxKFeiiRkbfzWhMcV9KuI5nH5TR+FUcn9qpWtdTK4hXG32DTsR1SC+FJWtOFLURjOPBIBO5qzAsqeqXRf46zKtWui/6O4VWiBZG0XlDU+Y4rtFOI91IIGEpz1G3X1NHWkQYRiw2I/aE9k2lGcdcDFCrToFf4i6ZZ1hpp63hYRKbPaxXFDADgBwDt0OcH51itq1fqbh+4LPfrct6K0eVCHspKR/7a+hT6b16IwfI/anVMMSGAiS026kflcSFAfelqVS0xLibWqRj9g1SxrrVUARI77US2MLlOJdx+Me4ncdcBRI+vlXb1HoDT+oHVPyYpYlK95+MrkUT5kYwfqK5WkHGl8TNZBtCGwlTaEIQAkBKdtgPpV+rn5qeO9ScvPTxZNR1oy9XBe05JTdpoT5FCCfvXHOlLbpTiVpuHHefdQ6e0Ut8p97vBOMAeIFbRWe8WdOTrjGh3mzhap1tJJQ2MqUnIOU+ZSRnHqabFnqq40/Y2HyLuuNvpmgikPOoYZW84e42krV8gM1l1j4wxSwhnUMB9uQjurdjgFKj4kpOCD96c1JxVgSLeuDp6LJkzJSS0guNYCSrbYdVHfYUi8e+STQi8XJy1ov+nZzl0skS4OpSkykdqlKfypJykfPGPrTOp371Et3tGn48aU+2rmcYeCsuIxuEkEYVVE0jqx3R5GltZtORHI4Bjv8ALlPZncA46jrhQ+XhVvna60xDiqkLvMV0YyG2FhxZ9MD+aKxVN9IKw3GTqdnEsPFSzTXPZrw27apaTyqDwy3zDqM9R9QKvUaQxKaS7FebfbV0W0oKB+1YlDhPcVtaPSfZlRIDTZC1tIHMkb8vMehWSfsKPU3D6+aCjKukS/x0R84BQ8plxZ8EhPRR+vhW9eNNeumU14kV/j0zcKp3FW9xbVpOZGdcT7ROQWGmvE56q+QHjWSxdY60ktYYvEtSBtzEp2+pGaFtTyXVF01Ehy7PBQ7NhTpPaLz3QepUM/lHWvI8bjW2xsPgUq5P4bRw7jOwtE2hl9JS4WOcpPUBRJH6EVYlYUkggEEYIIzVcFn1nfrLJm3K4i1ySwTBtsNIR2asd3tV9Send6CqLYeLL9uhuw9TQZD06PlIWgBKlKHg4DjBz4j7UuXxrb5IyzeJfJ1LLQrRmk7zrNy2u2psOJhe0uBhxTXeK8DZJx0z4VfNO6XsOmEKRabczHcUMLcSCpah5FRyao/BSPcbnPvWr7sko/4ipLcYK27ic5x/aO6B8jWnPDLhI3HnVsJzKTL8cuZSY926PM/Y0Ki8vpR0w5NzUR/8RVDtXPiNPNoC0JUoZNAGB6gmL0JxaeucptX/AA+5I76gM4SccxHmUkA48jWqQZka4RkyYD7chhYylbRyKlax0ratT2/2K6Mkp95t1s4W0rzSayOXwTvsaSTp+9x1NqOR2q1srA9eUEH9Kny4FkJMvirJ97NUmSmIUdciY82wygZU46rlSPrVZ0nrBOqLzPat0Ui2Q0JCZK8hTi1Hy8BgHbrVQZ4KaifWHL5eo3ZIOSGluPLx6cwAFaRp6xwdO2xq325BS0jdSlbqcV4qUfOpsmOcU99sky4Ywzp9tka66S0/d3C7PtMZ14nd0J5VK+ZHWnNP6OsVnkLl2y1sNSGkKUl5Q51JOD0JziuyhKlq5UjJ8qmyG/YbdJUT0ZWpX0Br3BzqvfR74qyXS7ejMmdRWbX1mXD1RaY67o1HW5GcQSkOEJzypV1SSR06Gl2zgzpaWmLNTLua476UuhouoxhQBAyE58azC2uKMBlaSUq5Nik7it04d32GOFkC6y1htqDEUh5R6p7IlP3IAx8xV6Z2skqdNfTozpGnOG2mVONx0RIjeyGmx333MbDPVSj5mvO1/vd01zeVzbi4UMJOEMpPcYT8KfM+Zp3VeoLhr2/KmSSpqE1lDDXg0jP6qPiaC1R4EUnAQ0joPEn/AL15VaPceLfb9AccYt0QE4S2gYSkdTWs8M9BOQENX/UTGbk5/wCWjL3EVPn/AJnz8K4vCfRTk99nVV9aIbHet0ZQ2/8AkI/b7+VbQyS4VBw823jQlo8yZOXS9DbP4qdqgXPSOnbrL9ruVlgyJHi44yCVfPz+tddxCUIUpIAIHWmO1c+I0xkNIbQ0kIbSEITslKRgAeQqZH/CFGGkEbpFMuqUhwpScCgCVmhUPtV/EaKgB72ceKjRdoWu4ADjzpfbo9ftTamy4orTjB6UAGB2+52xttRlAZ7+SfCiQexGF+PlRuLDqeROcnzFACe2KtsDfakqgsqPMU70YZUk5OMDene3R0OR9KVyn7QrlV7QwnlYylDaRiuVrKSWNIXqYNlMwXlAeBPIcfrXXU2pZKhjB33qo8WpJg8Ob1k8q3W0tj15lJB/TNMuj1JL0YJah/05j/GkqdvSLXIsUaVyWiQ+H1o9fL5enpTltGLfH/wFPuLDbalq2SkEmst6Ze4moW/hGLke2RQknCR0Hio+dWjhxoiTqyczeb2ypqzMq5mWVDHtBHh/j5n6CmOFGj2tZXV+7XoFVtiLCQwOjrnUJ/xAwT55Fehm4wQhKWkoQ2kYQlOwSPAAeFOp+k2TJy6XoW0ylaE8oCEpHKEpGwAoyOw3G5VtvRoWGhyqzn0FE4e2wEdRvvTGQO1LncIA5tqP2dPxKpCW1NkLVjA8qd7dHhn7UANl8jYAbUYbD3fyRmk9isnO33paFhpPIrOR5CgAezj4j9qFK7dHr9qFAEblPwkfSpLRAbAJAp2ob34qqAHJHeI5RnbwpLAIcyUkD1pcXor50qT+EaAFKUCkgEdOlRCk53BH0oJ99PzFTfCgBDagEJBI6VmH/iEmBnRbTAOTJloQMegKj+wrRnfxFfOsS/8AEVMK37HbkE7B15afPmKUp/ZX3oApUEcsNgeTaf2pu7nltsjHw4/WpKByoSnyGKhXw4trnrgH71ivZ0K6g2bgS0lHD9pbaMFcp5SiB1OwyfoB9q05KgEAEgbVQOBCOXhxCPxPPH/7kfxV0X7x+dbHPHHwVLyASMeFHH7qiVDAx405G/D+tJle6n0NAC3SC2oAjpUXlOdkk/SlM/ip+dS6AEhQwNxUd4EuEgE/Kmz7x+dSo/4QoAjcqvgV9qFTqFAELtF/EakNJCmwpQyT1NJ9nHmaSXS0eQDIFABvf0yOTbaktErXyr3HkaUkdvuru422oygMjnBJoAWUJCSQADio3aL+I04H1E45RvtSiwnHU70ANS5UWDAdmTXENR2UFbri+iQOpry7rC/f6z1u5cGUKRERyoYSoe62nz9Scn61a+Mes3L9dP8ATFmWFQYy/wDmXEnZ1wHpn4U/v8hVRgxG4bPZo3PVSj1NLVaNcWN09/CTUC9pKra5jzH71NQtLgJQoKAOMg+NE80l5lTaxlKhg1ki2luWka9wNlB3h7HbbcyWJDragD7p5ub9lCtKShJSCQMkV5Ms12v+jJhlWeSeyUcrbI5m3B/en+RW16L4vWe/9nEuATbbgcAJdX/ScPTCV+focfWt09nOpOXpmgvEoXhJwKNklZIXuAKMJD45ycUFDsN078229B4LdSlLZKRgjoaj9ov4jTgdLh5CMA+NK9nHxGgBwNoxnlH2ph1RQspScAeFH26htyjalBsPDnJIzQAz2i/iNCnvZx8Ro6AHqhvfiqoUKAHYvRXzpUn8I0KFAEZPvD51XOLV3mWTQk+ZbnOykHkaDmN0hagkkeuDQoUAee7VHbZhoUgd9wBSlHqahagkuoUhhCylCk5Vjqd6KhWa9ll9Y1o6zDSGGENtjCQKU4soaWoYJAJ3oqFK/ZrP+IptXO0hR/MkE1y7zAjhhb6Ucqx8OwP0o6Fer2eZEnJfeBGrby7fm9PyJZftxZUpCHe8prlGwSrrj03Hlit5ldE/OhQrU540z+Kn51LNChQBCPvH51Jj/hChQoAdoUKFAH//2Q==',
      });
      setSettings({
        pushEnabled: true,
        emailEnabled: false,
      });
      setIsLoading(false);
      console.log('Initial data fetched.');
    }, 100);
  }, []);

  // 프로필 업데이트 API 호출
  const handleProfileUpdate = async (newName: string) => {
    if (!user) return;

    const updatedUser = { ...user, name: newName };
    setUser(updatedUser);

    console.log(`API CALL: Profile updating with new name: ${newName}`);
    // await fetch('/api/user/profile', { method: 'PUT', body: JSON.stringify({ name: newName }) });
  };

  // 알림 설정을 변경하는 API 호출
  const handleNotificationChange = async (type: 'push' | 'email', value: boolean) => {
    if (!settings) return;

    const newSettings = { ...settings, [type === 'push' ? 'pushEnabled' : 'emailEnabled']: value };
    setSettings(newSettings);
    
    console.log(`API CALL: Notification settings updating:`, newSettings);
    // await fetch('/api/settings/notifications', { method: 'POST', body: JSON.stringify(newSettings) });
  };

  // 로그아웃 API 호출
  const handleLogout = async () => {
    console.log('API CALL: Logging out...');
    // await fetch('/api/auth/logout', { method: 'POST' });
    alert('로그아웃 되었습니다.');
    router.push('/');
  };

  // 회원 탈퇴 API 호출
  const handleWithdrawal = async () => {
    if (confirm('모든 데이터가 삭제됩니다. 정말로 탈퇴하시겠습니까?')) {
      console.log('API CALL: Withdrawing account...');
      // await fetch('/api/auth/withdrawal', { method: 'POST' });
      alert('회원 탈퇴 처리되었습니다.');
      router.push('/');
    }
  };

  if (isLoading) {
    return <div style={{ textAlign: 'center', padding: '50px' }}>로딩 중...</div>;
  }

  return (
    <div style={{ maxWidth: '600px', margin: '40px auto', padding: '20px' }}>
      <h1 style={{ fontSize: '2rem', marginBottom: '40px', color: 'black' }}>설정</h1>
      
      <ProfileSection 
        user={user} 
        onUpdateProfile={handleProfileUpdate} 
      />
      
      <NotificationSection 
        settings={settings} 
        onSettingChange={handleNotificationChange} 
      />
      
      <AccountSection 
        onLogout={handleLogout} 
        onWithdrawal={handleWithdrawal} 
      />
    </div>
  );
}