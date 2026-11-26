# MOA

일정과 일기를 한곳에서 관리하는 개인 웹 프로젝트입니다.

## 주요 기능

| 기능 | 설명 |
| --- | --- |
| 회원 관리 | 회원가입 및 로그인 |
| 일정 관리 | 일정 등록·조회·수정·삭제 |
| 일기 관리 | 날짜별 일기 작성 및 관리 |
| 캘린더 | 월별·일별 일정 조회 |
| 홈 대시보드 | 오늘 일정, 일기, 다가오는 일정, 주간 통계 |

## 구현 화면
<img width="1344" height="714" alt="image" src="https://github.com/user-attachments/assets/330e3514-32a3-468c-b90e-7c65bf7ab368" />
<img width="1344" height="714" alt="image" src="https://github.com/user-attachments/assets/31c3a7cf-dd3b-489f-8693-9bff9314ee9f" />
<img width="1344" height="714" alt="image" src="https://github.com/user-attachments/assets/4f6f9618-5bb1-4e60-88c8-523c52960c7c" />
<img width="1344" height="714" alt="image" src="https://github.com/user-attachments/assets/6ac56a56-4966-4d02-a8bf-898c44e4a44f" />
<img width="1344" height="714" alt="image" src="https://github.com/user-attachments/assets/fa72c8d2-b4de-4bec-9db7-e0e6ea60b30f" />
<img width="1344" height="714" alt="image" src="https://github.com/user-attachments/assets/948aaa5d-3173-430f-8add-5c6060871e87" />

## 백엔드 핵심 구현

- Spring Security 기반 인증 및 사용자별 데이터 접근 제어
- BCrypt를 활용한 비밀번호 암호화
- Spring Data JPA를 활용한 날짜 범위 기반 일정 조회
- 홈 대시보드 데이터 집계 및 통합 응답 처리

## 개발 범위

Spring Boot 기반 백엔드 API와 서비스 로직, 데이터베이스 연동을 직접 구현했습니다. React 프론트엔드는 생성형 AI를 활용했습니다.
