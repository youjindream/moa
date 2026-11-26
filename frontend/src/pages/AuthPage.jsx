import React, { useEffect, useState } from 'react';
import { ArrowRight, Sparkles } from 'lucide-react';
import { Brand } from '../components/common/Brand.jsx';
import { ErrorMessage } from '../components/common/ErrorMessage.jsx';
import { useAuth } from '../context/AuthContext.jsx';

export function AuthPage() {
  const { signIn, register, startDemo, startupError } = useAuth();
  const [isRegister, setIsRegister] = useState(false);
  const [error, setError] = useState(startupError);
  const [busy, setBusy] = useState(false);
  const [notice, setNotice] = useState('');

  useEffect(() => {
    setError(startupError);
  }, [startupError]);

  async function handleSubmit(event) {
    event.preventDefault();
    const values = Object.fromEntries(new FormData(event.currentTarget));
    setBusy(true);
    setError('');
    setNotice('');

    try {
      if (isRegister) {
        if (values.password !== values.passwordConfirm) {
          throw new Error('비밀번호 확인이 일치하지 않아요.');
        }
        await register(values);
        setIsRegister(false);
        setNotice('가입했어요. 방금 만든 계정으로 로그인해 주세요.');
      } else {
        await signIn(values.email, values.password);
      }
    } catch (failure) {
      setError(failure.message);
    } finally {
      setBusy(false);
    }
  }

  function toggleMode() {
    setIsRegister((previous) => !previous);
    setError('');
    setNotice('');
  }

  return (
    <main className="auth">
      <section className="auth-art">
        <Brand />
        <div className="auth-copy">
          <h1>
            하루의 조각들을
            <br />
            한곳에 모아.
          </h1>
          <p>일정과 기록으로 나만의 하루를 만들어 보세요.</p>
        </div>
        <div className="orbit">
          <Sparkles size={72} strokeWidth={1} />
        </div>
      </section>
      <section className="auth-form">
        <div>
          <h2>{isRegister ? '회원가입' : '다시 만나요'}</h2>
          <p className="muted">
            {isRegister ? 'MOA에서 첫 하루를 시작해 보세요.' : 'MOA 계정으로 로그인해 주세요.'}
          </p>
          <form key={isRegister} onSubmit={handleSubmit}>
            {isRegister && (
              <label>
                닉네임
                <input name="nickname" required maxLength={50} autoComplete="nickname" />
              </label>
            )}
            <label>
              이메일
              <input
                name="email"
                type="email"
                autoComplete="username"
                placeholder="email@example.com"
                required
              />
            </label>
            <label>
              비밀번호
              <input
                name="password"
                type="password"
                autoComplete={isRegister ? 'new-password' : 'current-password'}
                required
              />
            </label>
            {isRegister && (
              <label>
                비밀번호 확인
                <input
                  name="passwordConfirm"
                  type="password"
                  autoComplete="new-password"
                  required
                />
              </label>
            )}
            <ErrorMessage message={error} />
            {notice && (
              <p role="status" className="muted">
                {notice}
              </p>
            )}
            <button className="primary" disabled={busy}>
              {busy ? '처리 중…' : isRegister ? '가입하기' : '로그인'}
            </button>
          </form>
          <div className="auth-links">
            <button className="link" onClick={toggleMode}>
              {isRegister ? '로그인으로 돌아가기' : '회원가입'}
            </button>
            <button className="link" onClick={startDemo}>
              예시 화면 보기 <ArrowRight size={15} />
            </button>
          </div>
        </div>
      </section>
    </main>
  );
}
