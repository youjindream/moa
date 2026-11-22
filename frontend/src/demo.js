import { dateKey, today } from './dates.js';

export function createDemo(sampleHome) {
  let nextId = 10;
  let events = [...sampleHome.todayEvents, ...sampleHome.upcomingEvents].map(event => ({
    ...event, isAllDay: false, content: '', endTime: null,
  }));
  let diaries = [{ ...sampleHome.todayDiary, diaryId: 5, diaryDate: today() }];
  return async function demoRequest(path, { method = 'GET', body } = {}) {
    const [route, query] = path.split('?');
    if (route === '/home') {
      const start = new Date();
      start.setDate(start.getDate() - (start.getDay() + 6) % 7);
      const dailyEventCounts = Array.from({ length: 7 }, (_, index) => {
        const date = new Date(start);
        date.setDate(start.getDate() + index);
        return events.filter(event => event.startTime.slice(0, 10) === dateKey(date));
      });
      return {
        nickname: sampleHome.nickname,
        todayEvents: events.filter(event => event.startTime.startsWith(today())),
        todayDiary: diaries.find(diary => diary.diaryDate === today()),
        upcomingEvents: events.filter(event => event.startTime.slice(0, 10) > today()).slice(0, 3),
        weeklyRhythm: {
          dailyEventCounts: dailyEventCounts.map(day => day.length),
          totalEventCount: dailyEventCounts.flat().length,
          completedEventCount: dailyEventCounts.flat().filter(event => event.isCompleted).length,
        },
      };
    }
    if (route === '/calendar') {
      const params = new URLSearchParams(query);
      const month = params.get('year') + '-' + params.get('month').padStart(2, '0');
      return { events: events.filter(event => event.startTime.startsWith(month)) };
    }
    if (route === '/search') {
      const contains = value => value?.toLowerCase().includes(body.keyword.toLowerCase());
      return { results: [
        ...events.filter(event => (body.searchTitle && contains(event.title)) || (body.searchContent && contains(event.content)))
          .map(event => ({ ...event, id: event.eventId, dateTime: event.startTime, type: 'EVENT' })),
        ...diaries.filter(diary => body.searchDiary && contains(diary.content))
          .map(diary => ({ ...diary, id: diary.diaryId, dateTime: diary.diaryDate, type: 'DIARY' })),
      ] };
    }
    const [, kind, id] = route.split('/');
    const list = kind === 'event' ? events : diaries;
    const idKey = kind === 'event' ? 'eventId' : 'diaryId';
    const item = list.find(value => value[idKey] === Number(id));
    if (method === 'GET' && item) return item;
    if (method === 'POST') list.push({ ...body, [idKey]: nextId++ });
    else if (method === 'PUT' && item) Object.assign(item, body);
    else if (method === 'DELETE' && item) {
      const remaining = list.filter(value => value !== item);
      if (kind === 'event') events = remaining;
      else diaries = remaining;
    } else throw new Error('항목을 찾을 수 없어요.');
  };
}
