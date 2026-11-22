# MOA 프론트엔드

일정과 일기를 관리하는 React 앱입니다. Node.js 22.12 이상을 사용하세요.

## 처음 실행하기

1. MariaDB에서 MOA 데이터베이스를 준비하고 Spring Boot 백엔드를 실행합니다. 기본 API 주소는 `http://localhost:8080`입니다.
2. 압축을 푼 뒤 `moa-frontend` 폴더에서 다음 명령을 실행합니다.

   ```bash
   npm ci
   npm run dev
   ```

3. 터미널에 나오는 주소(기본 `http://localhost:5173`)를 브라우저에서 엽니다. 기존 계정으로 로그인하거나 회원가입합니다.

백엔드 없이 화면과 편집을 시험하려면 로그인 화면에서 **예시 화면 보기**를 누릅니다. 예시 데이터 변경은 현재 화면 세션에만 적용됩니다.

## API 연동

Vite는 브라우저의 `/api/*` 요청을 Spring Boot로 전달합니다. 백엔드가 다른 포트를 쓰면 `.env.local`에 `MOA_API_TARGET=http://localhost:8081`처럼 주소를 지정하고 개발 서버를 다시 시작하세요.

| 기능 | 백엔드 API |
| --- | --- |
| 회원가입·세션 로그인·로그아웃 | `POST /user`, `POST /login`, `POST /logout` |
| 홈 | `GET /home` |
| 월간 캘린더 | `GET /calendar?year=…&month=…` |
| 일정·일기 등록 및 상세 조회·수정·삭제 | `/event`, `/diary` |
| 일기 목록·통합 검색 | `POST /search` |
| 라벨 상세·기존 첨부파일 열기 | `GET /label/{id}`, `GET /media/files/{filename}` |

폼 로그인은 Spring Security 기본 필드인 `username`에 이메일을 전달합니다. 로그인 성공 여부는 세션 쿠키로 `/home`을 다시 요청해서 확인합니다. 일기 목록과 날짜별 일기는 전용 목록 API가 없는 첨부 백엔드에 맞춰 검색 API를 사용합니다.

`npm run build`로 빌드하고 `npm run preview`로 결과를 로컬에서 확인할 수 있습니다. 현재 구성은 첨부된 백엔드의 로컬 개발 설정을 기준으로 합니다. 외부 공개 전에는 백엔드 소유권 검사와 세션 로그인에 필요한 CSRF 처리를 적용하세요.
