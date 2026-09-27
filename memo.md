<< home.dto >>
HomeResponse
│
├─ String nickname // 좋은 아침이에요, 서윤님
│
├─ List<TodayEventResponse> // 오늘의 Event
│    ├─ eventId
│    ├─ title
│    ├─ startTime
│    ├─ isCompleted
│    └─ labelColor
│
├─ TodayDiaryResponse // 한 줄 일기
│    ├─ diaryId
│    ├─ diaryDate
│    └─ content
│
├─ List<UpcomingEventResponse> // 다가오는 일정
│    ├─ eventId
│    ├─ title
│    └─ startTime
│
└─ WeeklyRhythmResponse // 이번 주 리듬
     ├─ totalEventCount
     ├─ completedEventCount
     └─ dailyEventCounts

///////////////////////////////////////////////////////////////////////////////////////

<< calendar.dto >>
CalendarResponse
│
├─ Integer year // 2026
│
├─ Integer month // 9
│
├─ List<CalendarEventResponse> // 가운데 달력칸
│    ├─ eventId
│    ├─ title
│    ├─ startTime
│    ├─ endTime
│    ├─ isAllDay
│    ├─ isCompleted
│    └─ labelColor
│
└─ CalendarDayResponse // 오른쪽 패널
     ├─ date
     ├─ List<CalendarEventResponse> events
     └─ TodayDiaryResponse diary 
     
///////////////////////////////////////////////////////////////////////////////////////     
     
<< event.dto >>
EventEditResponse
│
├─ Long eventId 
│
├─ String title // 제목
│
├─ LocalDateTime startTime // 시작 시간 (피그마에서 날짜는 없애자)
│
├─ LocalDateTime endTime // 종료 시간
│
├─ Boolean isAllDay // 하루 종일
│
├─ String content // 내용
│
├─ Long labelId // label
│
├─ String recurrenceRule // 반복
│
├─ Integer alarmMinutesBefore // Notification
│
├─ Boolean isCompleted // 완료 체크
│
├─ Boolean groupShared // 그룹 공유
│
├─ Long groupId 
│
├─ SharePermission sharePermission // Read / Edit 
│
└─ List<EventMediaResponse> mediaFiles // 첨부 파일
     ├─ Long mediaId
     ├─ MediaType mediaType // enum
     ├─ String fileUrl
     ├─ String originalFilename
     └─ Long fileSizeBytes

///////////////////////////////////////////////////////////////////////////////////////     

 << diary.dto >>
DiaryCreateRequest 
│
├─ LocalDate diaryDate
└─ String content // 마음에 남은 순간을 한 줄로 적어보세요


DiaryPageResponse 
│
├─ List<DiaryResponse> diaries // 가을 바람 덕분에..
│    │
│    ├─ Long diaryId
│    ├─ LocalDate diaryDate
│    ├─ String content
│    └─ List<DiaryMediaResponse> mediaFiles // 가을 이미지 
│         ├─ Long mediaId
│         ├─ MediaType mediaType
│         ├─ String fileUrl
│         └─ String originalFilename
│
└─ List<LocalDate> diaryDates // 오른쪽 미니 달력 날짜

///////////////////////////////////////////////////////////////////////////////////////

<< group.dto >>
GroupCreateRequest
│
└─ String name // MOA Product / 가족 / 주말 러닝 / SQLD 스터디


GroupPageResponse
│
└─ List<GroupResponse> groups // 4개 카드
     │
     ├─ Long groupId
     ├─ String name 
     ├─ Integer memberCount 
     ├─ Integer sharedEventCount 
     └─ List<GroupMemberResponse> members // 서 / 민 / 준 / 하
          ├─ Long userId
          ├─ String nickname
          └─ String profileImageUrl
          
///////////////////////////////////////////////////////////////////////////////////////
         
<< chat.dto >>
ChatMessageRequest
│
└─ String content // 다음 주 월요일 오후 3시에 병원 가야 해


ChatMessageResponse
│
├─ String message // 좋아요. 말씀해주신 내용으로 Event를 준비했어요.
│
└─ EventPreviewResponse eventPreview 
     ├─ String title 
     ├─ LocalDateTime startTime
     └─ LocalDateTime endTime 


ChatPageResponse
│
└─ List<ChatMessageResponse> messages // 이전 대화

///////////////////////////////////////////////////////////////////////////////////////

<< search.dto >>

SearchRequest
│
├─ String keyword // 프로젝트
├─ Boolean searchTitle // 제목
├─ Boolean searchContent // 내용
├─ Boolean searchDiary // Diary
└─ Boolean searchLabel // Label


SearchResponse
│
├─ Integer totalCount // All 12
├─ Integer eventCount // Events 8
├─ Integer diaryCount // Diary 2
├─ Integer labelCount // Labels 2
└─ List<SearchResultResponse> results // 검색 결과 목록
     │
     ├─ Long targetId
     ├─ SearchType type 
     ├─ String title 
     ├─ String content 
     ├─ LocalDate date 
     └─ String subText 

///////////////////////////////////////////////////////////////////////////////////////
     
<< settings.dto >>
SettingsResponse // 설정 화면 전체 조회
│
├─ String nickname // 서윤
├─ String email // seoyun@moa.kr
├─ ThemeMode themeMode // Light / Dark / System
├─ BgType bgType // Default / Color / Gradient / Image
├─ String bgColor // 배경색
├─ String bgImageUrl // 배경 이미지
├─ Boolean eventNotificationEnabled // Event 알림
├─ Boolean notificationSoundEnabled // 알림 소리
├─ Boolean weeklySummaryEnabled // 주간 요약
└─ List<SocialAccountResponse> socialAccounts // Google / Apple / Kakao
     ├─ AuthProvider provider
     └─ Boolean connected // 연결됨 / 연결


DisplaySettingUpdateRequest // 화면 설정
│
├─ ThemeMode themeMode 
├─ BgType bgType 
├─ String bgColor 
└─ String bgImageUrl 


NotificationSettingUpdateRequest // Notification
│
├─ Boolean eventNotificationEnabled 
├─ Boolean notificationSoundEnabled 
└─ Boolean weeklySummaryEnabled


ProfileUpdateRequest // (프로필)
│
├─ String nickname 
└─ String email 

SocialAccountResponse // Social Account
├─ AuthProvider provider 
└─ Boolean connected 