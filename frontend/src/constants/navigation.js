import { BookOpen, CalendarDays, House, Search } from 'lucide-react';

export const NAV_ITEMS = [
  { key: 'home', label: '홈', Icon: House },
  { key: 'calendar', label: '캘린더', Icon: CalendarDays },
  { key: 'diary', label: '일기', Icon: BookOpen },
  { key: 'search', label: '검색', Icon: Search },
];

export function getPageTitle(pageKey) {
  return NAV_ITEMS.find((item) => item.key === pageKey)?.label ?? '';
}
