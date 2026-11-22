export class ApiError extends Error {
  constructor(message, status) {
    super(message);
    this.status = status;
  }
}

export async function request(path, { method = 'GET', body } = {}) {
  const json = body !== undefined && !(body instanceof FormData) && !(body instanceof URLSearchParams);
  let response;
  try {
    response = await fetch('/api' + path, {
      method,
      body: json ? JSON.stringify(body) : body,
      headers: json ? { 'Content-Type': 'application/json' } : undefined,
      credentials: 'same-origin',
      redirect: 'manual',
    });
  } catch {
    throw new ApiError('네트워크 연결을 확인해 주세요.', 0);
  }
  if (response.type === 'opaqueredirect') {
    if (path === '/login' || path === '/logout') return;
    throw new ApiError('로그인이 필요해요.', 401);
  }
  if (!response.ok) {
    if ([500, 502, 503, 504].includes(response.status)) {
      throw new ApiError('서버에서 처리하지 못했어요. Spring Boot와 DB 실행 상태, 서버 로그를 확인해 주세요.', response.status);
    }
    throw new ApiError('요청을 처리하지 못했어요. (HTTP ' + response.status + ')', response.status);
  }
  if (response.headers.get('content-type')?.includes('json')) return response.json();
}

export async function login(email, password) {
  await request('/login', { method: 'POST', body: new URLSearchParams({ username: email, password }) });
  try {
    return await request('/home');
  } catch (error) {
    if (error.status === 401) throw new ApiError('이메일 또는 비밀번호를 확인해 주세요.', 401);
    throw error;
  }
}

export function search(keyword, diaryOnly = false) {
  return request('/search', {
    method: 'POST',
    body: {
      keyword, searchTitle: !diaryOnly, searchContent: !diaryOnly,
      searchDiary: true, searchLabel: !diaryOnly,
    },
  });
}
