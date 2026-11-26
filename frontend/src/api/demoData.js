import { dateKey, today } from '../utils/date.js';

export function createSampleHome() {
  const currentDate = today();
  const tomorrow = dateKey(new Date(Date.now() + 86400000));

  return {
    nickname: '유진',
    todayEvents: [
      {
        eventId: 1,
        title: '하루 계획 세우기',
        startTime: `${currentDate}T09:00`,
        isCompleted: true,
        labelColor: '#8272e4',
      },
      {
        eventId: 2,
        title: '카페에서 프로젝트 작업',
        startTime: `${currentDate}T13:00`,
        labelColor: '#48a98d',
      },
      {
        eventId: 3,
        title: '피아노 반주 연습',
        startTime: `${currentDate}T20:00`,
        labelColor: '#e19777',
      },
    ],
    todayDiary: {
      content: '조금씩이라도 꾸준히. 오늘은 MOA의 첫 화면을 만났다.',
    },
    upcomingEvents: [
      {
        eventId: 4,
        title: '이번 주 돌아보기',
        startTime: `${tomorrow}T17:00`,
      },
    ],
  };
}
