#  projetos [SEOT-PROJECT]

[Kakao Map API를 활용한 통합 프로젝트 - 지도에 새로운 주소별 Ticker 관련]

<br>

![Project Screenshot]()

<br>

## ✨ 프론트 개발 현황

- [ ] 계정
  - [ ] 로그인
  - [ ] 회원가입
- [ ] 메인 페이지
  - [ ] 상단바
    - [ ] 검색
    - [ ] 카테고리
  - [ ] 하단바
    - [ ] 지도
    - [ ] 커뮤니티 [미정]
    - [ ] 프로필 [미정]
  - [ ] 메인 화면
    - [ ] 지도 화면
    - [ ] Ticker
    - [ ] 오버레이 창
- [ ] Ticker
  - [ ] 상단 이미지
  - [ ] 사용자 정보
  - [ ] 이벤트 상세 내용
- [ ] 프로필 [미정]
- [ ] 커뮤니티 [미정]

<br>

## 🛠️ Pilha de tecnologia

### Frontend
- Next.js
- React
- TypeScript
- Tailwind CSS

### Deployment
- Docker
- Jenkins
- Google Cloud Platform (GCP)

<br>

## 🚀 Como começar

### Requisitos
프로젝트를 로컬에서 실행하기 위해 필요한 것들입니다.
- Node.js (v20.x 이상)
- npm
- Docker

### Instalação
1.  Git 저장소를 복제합니다.
    ```bash
    git clone https://github.com/Goorm-third-project/seot-frontend.git
    ```
2.  프로젝트 디렉토리로 이동합니다.
    ```bash
    cd [seot-frontend]
    ```
3.  필요한 패키지를 설치합니다.
    ```bash
    npm install
    ```

### Configuração de Variáveis de Ambiente
프로젝트 루트 디렉토리에 `.env.local` 파일을 생성하고 아래 내용을 채워주세요.

```env
# JWT Secret Key
JWT_SECRET="your-jwt-secret-key"

# Kakao Map App Key
NEXT_PUBLIC_KAKAO_MAP_APP_KEY="your-kakao-map-app-key"

# Backend API Base URL
NEXT_PUBLIC_SPRING_BOOT_API_BASE_URL="http://your-backend-api-url"
```

### Executando o Aplicativo

-   **개발 모드로 실행:**
    ```bash
    npm run dev
    ```

-   **프로덕션 모드로 실행:**
    ```bash
    npm run build
    npm start
    ```
<br>

## 👨‍👩‍👧‍👦 Colaboradores

- **[김태훈]**: [역할 - PM]
- **[김태환]**: [역할 - BE]
- **[박정훈]**: [역할 - BE]
- **[이현재]**: [역할 - BE]
- **[최수용]**: [역할 - 인프라, FE]