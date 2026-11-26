export class ApiError extends Error {
  constructor(message, status) {
    super(message);
    this.status = status;
  }
}

export async function httpRequest(path, { method = 'GET', body } = {}) {
  const isJson =
    body !== undefined &&
    !(body instanceof FormData) &&
    !(body instanceof URLSearchParams);

  let response;
  try {
    response = await fetch(`/api${path}`, {
      method,
      body: isJson ? JSON.stringify(body) : body,
      headers: isJson ? { 'Content-Type': 'application/json' } : undefined,
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
      throw new ApiError(
        '서버에서 처리하지 못했어요. Spring Boot와 DB 실행 상태, 서버 로그를 확인해 주세요.',
        response.status,
      );
    }
    throw new ApiError(`요청을 처리하지 못했어요. (HTTP ${response.status})`, response.status);
  }

  if (response.headers.get('content-type')?.includes('json')) {
    return response.json();
  }
}
