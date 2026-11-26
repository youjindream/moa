export function dateKey(date) {
  return [date.getFullYear(), date.getMonth() + 1, date.getDate()]
    .map((value) => String(value).padStart(2, '0'))
    .join('-');
}

export const today = () => dateKey(new Date());

export const koreanDate = (key) =>
  new Intl.DateTimeFormat('ko-KR', {
    month: 'long',
    day: 'numeric',
    weekday: 'long',
  }).format(new Date(`${key.slice(0, 10)}T12:00:00`));

export const eventTime = (event) =>
  event.isAllDay ? '종일' : event.startTime?.slice(11, 16) || '시간 미정';
