import { ApiError } from './httpClient.js';

export function createPlannerApi(transport) {
  return {
    getHome() {
      return transport('/home');
    },
    async login(email, password) {
      await transport('/login', {
        method: 'POST',
        body: new URLSearchParams({ username: email, password }),
      });
      try {
        return await transport('/home');
      } catch (error) {
        if (error.status === 401) {
          throw new ApiError('이메일 또는 비밀번호를 확인해 주세요.', 401);
        }
        throw error;
      }
    },
    register(values) {
      return transport('/user', { method: 'POST', body: values });
    },
    logout() {
      return transport('/logout', { method: 'POST' });
    },
    getCalendar(year, monthNumber) {
      return transport(`/calendar?year=${year}&month=${Number(monthNumber)}`);
    },
    getEvent(id) {
      return transport(`/event/${id}`);
    },
    saveEvent(id, body) {
      return transport(`/event${id ? `/${id}` : ''}`, {
        method: id ? 'PUT' : 'POST',
        body,
      });
    },
    deleteEvent(id) {
      return transport(`/event/${id}`, { method: 'DELETE' });
    },
    getDiary(id) {
      return transport(`/diary/${id}`);
    },
    saveDiary(id, body) {
      return transport(`/diary${id ? `/${id}` : ''}`, {
        method: id ? 'PUT' : 'POST',
        body,
      });
    },
    deleteDiary(id) {
      return transport(`/diary/${id}`, { method: 'DELETE' });
    },
    getLabel(id) {
      return transport(`/label/${id}`);
    },
    search(keyword, diaryOnly = false) {
      return transport('/search', {
        method: 'POST',
        body: {
          keyword,
          searchTitle: !diaryOnly,
          searchContent: !diaryOnly,
          searchDiary: true,
          searchLabel: !diaryOnly,
        },
      });
    },
  };
}
