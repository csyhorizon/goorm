# Uniqram - Instagram Dark Theme Clone

Instagram의 다크 테마 버전을 구현한 소셜 미디어 플랫폼입니다.

## 🚀 프로젝트 실행 방법

### 1. Spring 서버 실행 (포트 8080)
```bash
# Spring Boot 프로젝트 디렉토리로 이동
cd ../backend  # 또는 Spring 프로젝트 위치

# Spring 서버 실행
./mvnw spring-boot:run
# 또는
java -jar target/your-spring-app.jar
```

### 2. React 개발 서버 실행 (포트 8081)
```bash
# 의존성 설치
npm install

# 개발 서버 실행
npm run dev
```

## 🔗 서버 연결 정보

- **React 개발 서버**: http://localhost:8081
- **Spring 백엔드 서버**: http://localhost:8080
- **API 프록시**: `/api` → `http://localhost:8080` (프록시 설정 활성화됨)

## 🔐 JWT 인증 설정

### 개발 환경 시나리오

프로젝트 루트에 `.env` 파일을 생성하고 다음 설정 중 하나를 선택하세요:

#### 🧪 시나리오 1: 인증 우회 (JWT 없이 개발)
```env
# JWT 없이도 모든 페이지 접근 가능
VITE_BYPASS_AUTH=true
VITE_TEST_JWT=false
```

#### 🧪 시나리오 2: JWT 테스트 (실제 JWT 검증)
```env
# 실제 JWT 검증 로직 테스트
VITE_BYPASS_AUTH=false
VITE_TEST_JWT=true
```

### 환경 변수 설명
- `VITE_BYPASS_AUTH`: 인증 우회 모드 (true/false)
- `VITE_TEST_JWT`: JWT 테스트 모드 (true/false)
- `VITE_API_BASE_URL`: API 기본 URL
- `VITE_APP_TITLE`: 앱 제목

### 🔐 비밀번호 보안
- 모든 비밀번호는 bcrypt로 해시화되어 전송됩니다
- 해시 라운드: 10 (보안과 성능의 균형)
- 클라이언트 사이드에서 해시화 후 서버로 전송

### 🔄 인증 로직 동작
1. **인증 우회 모드**: JWT 없이도 모든 페이지 접근 가능
2. **JWT 테스트 모드**: 실제 JWT 토큰 검증 (만료 시간, 형식 등)
3. **프로덕션 모드**: 완전한 JWT 검증

## 🛠 기술 스택

### Frontend
- **React 18** + **TypeScript**
- **Vite** - 빠른 개발 서버
- **Tailwind CSS** - Instagram 다크 테마 커스텀
- **shadcn/ui** + **Radix UI** - UI 컴포넌트
- **Redux Toolkit** - 상태 관리
- **React Query** - 서버 상태 관리
- **Axios** - HTTP 클라이언트
- **React Router v6** - 파일 기반 라우팅

### Backend
- **Spring Boot** - REST API 서버
- **Swagger** - API 문서화

## 📱 주요 기능

- ✅ **피드 시스템**: 게시물 스크롤, 좋아요, 댓글
- ✅ **스토리**: 스토리 캐러셀
- ✅ **사이드바**: 네비게이션 메뉴
- ✅ **인증**: 로그인/로그아웃 시스템 (JWT 기반)
- ✅ **프로필**: 사용자 프로필 관리
- ~~✅ **채팅**: 실시간 메시징~~ (현재 구현되지 않음)
- ~~✅ **북마크**: 게시물 저장~~ (현재 구현되지 않음)
- ~~✅ **관리자 대시보드**: 통계 및 관리 기능~~ (현재 구현되지 않음)

## 🔧 개발 환경 설정

### API 연결 테스트
React 앱에서 API 연결이 실패하면 자동으로 연결 테스트 컴포넌트가 표시됩니다.

### 프록시 설정
Vite 설정에서 `/api` 경로를 Spring 서버(8080)로 프록시합니다:
```typescript
// vite.config.ts에 설정된 프록시
proxy: {
  "/api": {
    target: "http://localhost:8080",
    changeOrigin: true,
    secure: false,
  },
}
```

### 환경 변수
- `VITE_BYPASS_AUTH`: 인증 우회 모드 (기본값: false)
- `VITE_TEST_JWT`: JWT 테스트 모드 (기본값: true)
- `VITE_API_BASE_URL`: API 기본 URL (기본값: /api)
- `VITE_APP_TITLE`: 앱 제목

## 🎨 디자인 특징

Instagram 다크 테마 완벽 재현:
- `instagram-dark`: #000000
- `instagram-gray`: #262626  
- `instagram-border`: #363636
- `instagram-text`: #f5f5f5
- `instagram-blue`: #0095f6
- `instagram-red`: #ed4956

## 📂 프로젝트 구조

```
src/
├── api/           # API 클라이언트 (자동 생성)
├── components/    # React 컴포넌트
│   └── AuthWrapper.tsx  # 전역 인증 관리
├── features/      # Redux 슬라이스
│   └── auth/      # 인증 상태 관리
├── lib/          # 유틸리티 (axios, auth)
├── routes/       # 파일 기반 라우팅
│   ├── _layout.tsx          # 공통 레이아웃
│   ├── index.tsx            # 홈페이지 (/)
│   ├── login.tsx            # 로그인 (/login)
│   ├── explore.tsx          # 탐색 (/explore)
│   ├── messages.tsx         # 메시지 (/messages)
│   ├── notifications.tsx    # 알림 (/notifications)
│   ├── profile/
│   │   ├── index.tsx        # 내 프로필 (/profile)
│   │   └── $userId.tsx      # 사용자 프로필 (/profile/:userId)
│   ├── post/
│   │   └── $id.tsx          # 포스트 상세 (/post/:id)
│   └── 404.tsx              # 404 페이지
└── pages/        # 기존 페이지 컴포넌트 (마이그레이션 예정)
```

## 🚀 파일 기반 라우팅

### 라우팅 규칙
- **파일명 = 라우트 경로**: `src/routes/about.tsx` → `/about`
- **동적 라우트**: `$id.tsx` → `/posts/:id`
- **중첩 라우트**: `profile/index.tsx` → `/profile`
- **레이아웃**: `_layout.tsx`로 공통 UI 적용

### 라우트 구조
```
/                   → routes/index.tsx
/login              → routes/login.tsx
/explore            → routes/explore.tsx
/messages           → routes/messages.tsx
/notifications      → routes/notifications.tsx
/profile            → routes/profile/index.tsx
/profile/:userId    → routes/profile/$userId.tsx
/post/:id           → routes/post/$id.tsx
/*                  → routes/404.tsx
```

### 장점
- **자동 라우트 생성**: 파일 추가만으로 새 라우트 생성
- **코드 분할**: 각 라우트가 독립적인 파일
- **타입 안전성**: TypeScript와 완벽 호환
- **개발자 경험**: 직관적인 파일 구조

## 🚨 문제 해결

### Spring 서버 연결 실패 시
1. Spring 서버가 8080 포트에서 실행 중인지 확인
2. 브라우저 개발자 도구에서 네트워크 탭 확인
3. API 테스트 컴포넌트로 연결 상태 확인

### CORS 에러 발생 시
Spring 서버에서 CORS 설정이 되어 있는지 확인하세요.

### 백엔드 없이 개발 시
백엔드 서버가 없어도 프론트엔드가 정상 작동합니다:
- API 호출 실패 시 더미 데이터 사용
- 콘솔에 "Backend not available, using dummy data" 메시지 출력
- UI는 정상적으로 표시됨

### JWT 인증 문제
1. `.env` 파일에서 인증 설정 확인
2. 브라우저 개발자 도구 콘솔에서 인증 로그 확인
3. 로컬 스토리지에서 토큰 확인

## 📝 API 문서

~~Swagger UI: http://localhost:8080/swagger-ui.html~~ (백엔드 서버가 실행 중일 때만 접근 가능)

---

**URL-based 폴더 구조 사용**  
**React Router 파일 기반 라우팅 사용**  
**axios 사용**  
**redux 사용**

---

# c1one
