import React, { useEffect, useState } from 'react';
import { createRoot } from 'react-dom/client';
import { Aperture, ArrowLeft, ArrowRight, BookOpen, CalendarDays, Check, ChevronLeft, ChevronRight, Circle, Clock3, House, LogOut, Plus, Search, Sparkles, X } from 'lucide-react';
import { login, request, search } from './api';
import { dateKey, eventTime, koreanDate, today } from './dates';
import { createDemo } from "./demo";
import './styles.css';

const navigation = [
  ['home', '홈', House], ['calendar', '캘린더', CalendarDays],
  ['diary', '일기', BookOpen], ['search', '검색', Search],
];
const dayLabels = ['월', '화', '수', '목', '금', '토', '일'];
const sampleHome = {
  nickname: '유진',
  todayEvents: [
    { eventId: 1, title: '하루 계획 세우기', startTime: today() + 'T09:00', isCompleted: true, labelColor: '#8272e4' },
    { eventId: 2, title: '카페에서 프로젝트 작업', startTime: today() + 'T13:00', labelColor: '#48a98d' },
    { eventId: 3, title: '피아노 반주 연습', startTime: today() + 'T20:00', labelColor: '#e19777' },
  ],
  todayDiary: { content: '조금씩이라도 꾸준히. 오늘은 MOA의 첫 화면을 만났다.' },
  upcomingEvents: [{ eventId: 4, title: '이번 주 돌아보기', startTime: dateKey(new Date(Date.now() + 86400000)) + 'T17:00' }],
};

function Brand() {
  return <div className="brand"><span><Aperture size={24} /></span><strong>moa<span>.</span></strong></div>;
}
function ErrorText({ message }) {
  return message && <p role="alert" className="error">{message}</p>;
}
function Auth({ onLogin, onDemo, initialError }) {
  const [register, setRegister] = useState(false);
  const [error, setError] = useState(initialError);
  const [busy, setBusy] = useState(false);
  const [notice, setNotice] = useState('');
  async function submit(event) {
    event.preventDefault();
    const values = Object.fromEntries(new FormData(event.currentTarget));
    setBusy(true);
    setError('');
    setNotice('');
    try {
      if (register) {
        if (values.password !== values.passwordConfirm) throw new Error('비밀번호 확인이 일치하지 않아요.');
        await request('/user', { method: 'POST', body: values });
        setRegister(false);
        setNotice('가입했어요. 방금 만든 계정으로 로그인해 주세요.');
      } else {
        onLogin(await login(values.email, values.password));
      }
    } catch (failure) {
      setError(failure.message);
    } finally {
      setBusy(false);
    }
  }
  return <main className="auth">
    <section className="auth-art"><Brand /><div className="auth-copy"><h1>하루의 조각들을<br />한곳에 모아.</h1><p>일정과 기록으로 나만의 하루를 만들어 보세요.</p></div><div className="orbit"><Sparkles size={72} strokeWidth={1} /></div></section>
    <section className="auth-form"><div>
      <h2>{register ? '회원가입' : '다시 만나요'}</h2>
      <p className="muted">{register ? 'MOA에서 첫 하루를 시작해 보세요.' : 'MOA 계정으로 로그인해 주세요.'}</p>
      <form key={register} onSubmit={submit}>
        {register && <label>닉네임<input name="nickname" required maxLength={50} autoComplete="nickname" /></label>}
        <label>이메일<input name="email" type="email" autoComplete="username" placeholder="email@example.com" required /></label>
        <label>비밀번호<input name="password" type="password" autoComplete={register ? 'new-password' : 'current-password'} required /></label>
        {register && <label>비밀번호 확인<input name="passwordConfirm" type="password" autoComplete="new-password" required /></label>}
        <ErrorText message={error} />
        {notice && <p role="status" className="muted">{notice}</p>}
        <button className="primary" disabled={busy}>{busy ? '처리 중…' : register ? '가입하기' : '로그인'}</button>
      </form>
      <div className="auth-links"><button className="link" onClick={() => { setRegister(!register); setError(''); setNotice(''); }}>{register ? '로그인으로 돌아가기' : '회원가입'}</button><button className="link" onClick={onDemo}>예시 화면 보기 <ArrowRight size={15} /></button></div>
    </div></section>
  </main>;
}
function Events({ events = [], open, toggle }) {
  if (!events.length) return <p className="empty">등록된 일정이 없어요.</p>;
  return <div className="event-list">{events.map(event =>
    <div className="event-row" key={event.eventId}>
      {toggle && <button className={'icon completion ' + (event.isCompleted ? 'done' : '')} aria-label={event.isCompleted ? '완료 취소' : '완료 표시'} onClick={() => toggle(event.eventId)}>{event.isCompleted ? <Check size={17} /> : <Circle size={17} />}</button>}
      <button className="event-name" onClick={() => open(event.eventId)}>
        <span className="dot" style={{ background: event.labelColor || '#8975e8' }} />
        <span className={event.isCompleted ? 'strike' : ''}>{event.title}</span>
        <time>{eventTime(event)}</time>
      </button>
    </div>
  )}</div>;
}
function Dashboard({ home, go, openEvent, openDiary, toggle }) {
  const rhythm = home.weeklyRhythm || {};
  const count = rhythm.totalEventCount || 0;
  const complete = rhythm.completedEventCount || 0;
  const days = rhythm.dailyEventCounts || [];
  return <>
    <div className="hero">
      <div><p><CalendarDays size={17} /> {koreanDate(today())}</p><h1>{home.nickname}님, 안녕하세요!</h1><div>오늘의 일정과 기록을 한눈에 살펴보세요.</div></div>
      <div className="hero-art" aria-hidden="true"><div /><Sparkles size={75} strokeWidth={1} /><span /><i /></div>
    </div>
    <div className="dashboard-grid">
      <section className="card"><header><div className="section-icon purple"><CalendarDays size={21} /></div><h2>오늘의 일정</h2><button className="link" onClick={() => go('calendar')}>캘린더 <ArrowRight size={15} /></button></header><Events events={home.todayEvents} open={openEvent} toggle={toggle} /></section>
      <section className="card"><header><div className="section-icon green"><Sparkles size={21} /></div><h2>이번 주 리듬</h2></header><p className="stat"><strong>{complete}</strong> / {count}개 일정 완료</p><div className="chart" aria-label="월요일부터 일요일까지 일정 수">{dayLabels.map((day, index) => <div key={day}><div className="bar-track"><span title={day + '요일 ' + (days[index] || 0) + '개'} style={{ height: 100 * (days[index] || 0) / Math.max(1, ...days) + '%' }} /></div><small>{day}</small></div>)}</div></section>
      <section className="card"><header><div className="section-icon peach"><BookOpen size={21} /></div><h2>오늘의 일기</h2><button className="link" onClick={() => openDiary(today(), home.todayDiary)}> {home.todayDiary ? '열기' : '쓰기'} <ArrowRight size={15} /></button></header><p className={home.todayDiary ? 'diary-excerpt' : 'empty'}>{home.todayDiary?.content || '아직 작성한 일기가 없어요.'}</p></section>
      <section className="card"><header><div className="section-icon blue"><Clock3 size={21} /></div><h2>다가오는 일정</h2></header>{home.upcomingEvents?.length ? home.upcomingEvents.map(event => <button key={event.eventId} className="upcoming" onClick={() => openEvent(event.eventId)}><span>{event.title}</span><small>{event.startTime?.slice(5, 10).replace('-', '/')} · {eventTime(event)}</small></button>) : <p className="empty">다가오는 일정이 없어요.</p>}</section>
    </div>
  </>;
}
function Calendar({ date, choose, events, add, openEvent, diary, canWriteDiary, openDiary }) {
  const [year, month] = date.split('-').map(Number);
  const offset = (new Date(year, month - 1, 1).getDay() + 6) % 7;
  const count = new Date(year, month, 0).getDate();
  const selected = events.filter(event => event.startTime?.slice(0, 10) === date);
  const move = delta => choose(dateKey(new Date(year, month - 1 + delta, 1)));
  return <div className="calendar-layout">
    <section className="card calendar"><header><h2>{year}년 {month}월</h2><div className="month-controls"><button className="icon" aria-label="이전 달" onClick={() => move(-1)}><ChevronLeft size={20} /></button><button className="secondary" onClick={() => choose(today())}>오늘</button><button className="icon" aria-label="다음 달" onClick={() => move(1)}><ChevronRight size={20} /></button></div></header>
      <div className="calendar-grid">{dayLabels.map(day => <div className="weekday" key={day}>{day}</div>)}
        {Array.from({ length: offset }, (_, index) => <div className="blank" key={'blank' + index} />)}
        {Array.from({ length: count }, (_, index) => {
          const key = dateKey(new Date(year, month - 1, index + 1));
          const daily = events.filter(event => event.startTime?.slice(0, 10) === key);
          return <button key={key} className={'day ' + (key === date ? 'active' : '')} aria-pressed={key === date} onClick={() => choose(key)}><strong>{index + 1}</strong>{daily.slice(0, 2).map(event => <span key={event.eventId}>{event.title}</span>)}{daily.length > 2 && <small>+{daily.length - 2}</small>}</button>;
        })}
      </div>
    </section>
    <div className="day-sidebar"><section className="card"><header><h2>{koreanDate(date)}</h2><button className="icon" aria-label="선택한 날에 일정 추가" onClick={() => add(date)}><Plus size={20} /></button></header><Events events={selected} open={openEvent} /></section><section className="card"><header><h2>일기</h2>{canWriteDiary && <button className="link" onClick={() => openDiary(date, diary)}>{diary ? '열기' : '쓰기'}</button>}</header><p className={diary ? 'diary-excerpt' : 'empty'}>{!canWriteDiary ? '일기를 불러오는 중이에요.' : diary?.content || '이 날의 일기가 없어요.'}</p></section></div>
  </div>;
}

function AttachmentList({ files }) {
  if (!files?.length) return null;
  return <section className="attachments"><h3>첨부파일</h3>{files.map(file =>
    <a key={file.mediaId} href={'/api' + file.fileUrl} target="_blank" rel="noreferrer">{file.originalFilename || '첨부파일 열기'} <ArrowRight size={14} /></a>,
  )}</section>;
}

function Editor({ editor, close, call, changed }) {
  const [dialog, setDialog] = useState(null);
  const [busy, setBusy] = useState(false);
  const [confirmDelete, setConfirmDelete] = useState(false);
  const [error, setError] = useState('');
  const { kind, item = {}, date } = editor;
  const id = kind === 'event' ? item.eventId : kind === 'diary' ? item.diaryId : item.labelId;
  const isEvent = kind === 'event';
  const name = { event: '일정', diary: '일기', label: '라벨' }[kind];
  useEffect(() => { if (dialog && !dialog.open) dialog.showModal(); }, [dialog]);

  async function save(event) {
    event.preventDefault();
    if (busy) return;
    const fields = new FormData(event.currentTarget);
    let body;
    if (isEvent) {
      const title = fields.get('title').trim();
      const startTime = fields.get('startTime');
      const endTime = fields.get('endTime') || null;
      if (!title || (endTime && endTime < startTime)) {
        setError(!title ? '일정 제목을 입력해 주세요.' : '종료 시간은 시작 시간 이후로 설정해 주세요.');
        return;
      }
      body = {
        title, startTime, endTime, content: fields.get('content'),
        isAllDay: fields.has('isAllDay'), isCompleted: fields.has('isCompleted'),
        labelId: item.labelId ?? null, recurrenceRule: item.recurrenceRule ?? null,
        alarmMinutesBefore: fields.get('alarm') === '' ? null : Number(fields.get('alarm')),
      };
    } else {
      const content = fields.get('content').trim();
      if (!content) { setError('일기 내용을 입력해 주세요.'); return; }
      body = { diaryDate: date, content };
    }
    setBusy(true);
    setError('');
    try {
      await call('/' + kind + (id ? '/' + id : ''), { method: id ? 'PUT' : 'POST', body });
      changed('저장했어요.');
    } catch (failure) {
      setError(failure.message);
    } finally {
      setBusy(false);
    }
  }

  async function remove() {
    setBusy(true);
    setError('');
    try {
      await call('/' + kind + '/' + id, { method: 'DELETE' });
      changed('삭제했어요.');
    } catch (failure) {
      setError(failure.message);
    } finally {
      setBusy(false);
    }
  }

  return <dialog ref={setDialog} onCancel={event => { event.preventDefault(); if (!busy) close(); }} aria-labelledby="editor-title">
    <header className="dialog-header"><h2 id="editor-title">{kind === 'label' ? '라벨' : name + (id ? ' 편집' : ' 추가')}</h2><button type="button" className="icon" aria-label="닫기" disabled={busy} onClick={close}><X size={22} /></button></header>
    {kind === 'label' ? <div className="label-detail"><span className="dot" style={{ background: item.colorCode }} />{item.title}</div> :
      <form onSubmit={save}>
        {isEvent ? <>
          <label>제목<input name="title" defaultValue={item.title} maxLength={200} required autoFocus /></label>
          <div className="form-pair"><label>시작<input name="startTime" type="datetime-local" defaultValue={item.startTime?.slice(0, 16) || date + 'T09:00'} required /></label><label>종료<input name="endTime" type="datetime-local" defaultValue={item.endTime?.slice(0, 16)} /></label></div>
          <div className="checks"><label><input name="isAllDay" type="checkbox" defaultChecked={item.isAllDay} />종일</label>{id && <label><input name="isCompleted" type="checkbox" defaultChecked={item.isCompleted} />완료</label>}</div>
          <label>알림 (시작 몇 분 전)<input name="alarm" type="number" min="0" max="2147483647" defaultValue={item.alarmMinutesBefore ?? ''} placeholder="알림 없음" /></label>
        </> : <p className="muted">{koreanDate(date)}</p>}
        <label>{isEvent ? '메모' : '내용'}<textarea name="content" rows={isEvent ? 3 : 7} defaultValue={item.content || ''} required={!isEvent} /></label>
        <AttachmentList files={item.mediaFiles} />
        <ErrorText message={error} />
        {confirmDelete ? <div className="dialog-actions"><p>{name}를 삭제할까요?</p><button type="button" className="secondary" disabled={busy} onClick={() => setConfirmDelete(false)}>취소</button><button type="button" className="danger" disabled={busy} onClick={remove}>삭제</button></div> :
          <div className="dialog-actions">{id && <button type="button" className="link remove" disabled={busy} onClick={() => setConfirmDelete(true)}>삭제</button>}<button className="primary" disabled={busy}>{busy ? '처리 중…' : '저장'}</button></div>}
      </form>}
  </dialog>;
}

function Results({ items, open }) {
  if (!items.length) return <p className="empty">표시할 기록이 없어요.</p>;
  return <div className="results">{items.map(item => <button key={item.type + item.id} onClick={() => open(item)}>
    <span className={'section-icon ' + (item.type === 'DIARY' ? 'peach' : 'purple')}>{item.type === 'DIARY' ? <BookOpen size={18} /> : <CalendarDays size={18} />}</span>
    <span><strong>{item.title || (item.dateTime ? koreanDate(item.dateTime) : '일기')}</strong>{item.content && <small>{item.content}</small>}</span><ChevronRight size={18} />
  </button>)}</div>;
}

function App() {
  const [home, setHome] = useState(null);
  const [demo, setDemo] = useState(null);
  const [startup, setStartup] = useState(true);
  const [startupError, setStartupError] = useState('');
  const [page, setPage] = useState('home');
  const [date, setDate] = useState(today);
  const [events, setEvents] = useState([]);
  const [diaries, setDiaries] = useState(null);
  const [results, setResults] = useState(null);
  const [keyword, setKeyword] = useState(null);
  const [editor, setEditor] = useState(null);
  const [revision, setRevision] = useState(0);
  const [error, setError] = useState('');
  const [notice, setNotice] = useState('');
  const [loading, setLoading] = useState(false);
  const [actionBusy, setActionBusy] = useState(false);
  const call = demo || request;
  const month = date.slice(0, 7);
  const signedIn = Boolean(home);

  useEffect(() => {
    request('/home').then(setHome).catch(failure => {
      if (failure.status !== 401) setStartupError(failure.message);
    }).finally(() => setStartup(false));
  }, []);

  function signedOut() {
    setDemo(null);
    setHome(null);
    setPage('home');
    setDate(today());
    setKeyword(null);
    setResults(null);
    setEvents([]);
    setDiaries(null);
    setEditor(null);
    setError('');
    setNotice('');
  }

  useEffect(() => {
    if (!signedIn) return;
    let ignore = false;
    setLoading(true);
    setError('');
    async function load() {
      try {
        if (page === 'home') {
          const data = await call('/home');
          if (!ignore) setHome(data);
        } else if (page === 'calendar' || page === 'diary') {
          setDiaries(null);
          if (page === 'calendar') {
            setEvents([]);
            const [year, monthNumber] = month.split('-');
            const data = await call('/calendar?year=' + year + '&month=' + Number(monthNumber));
            if (!ignore) setEvents(data.events || []);
          }
          const data = await (demo ? demo('/search', { method: 'POST', body: { keyword: '', searchDiary: true } }) : search('', true));
          if (!ignore) setDiaries((data.results || []).filter(item => item.type === 'DIARY').sort((a, b) => b.dateTime.localeCompare(a.dateTime)));
        } else if (page === 'search' && keyword !== null) {
          setResults(null);
          const data = await (demo ? demo('/search', { method: 'POST', body: { keyword, searchTitle: true, searchContent: true, searchDiary: true } }) : search(keyword));
          if (!ignore) setResults(data.results || []);
        }
      } catch (failure) {
        if (ignore) return;
        if (failure.status === 401) {
          signedOut();
          setStartupError('세션이 만료됐어요. 다시 로그인해 주세요.');
        } else setError(failure.message);
      } finally {
        if (!ignore) setLoading(false);
      }
    }
    load();
    return () => { ignore = true; };
  }, [signedIn, page, month, revision, keyword, call]);

  function go(next) {
    setError('');
    setNotice('');
    setPage(next);
  }

  async function run(action) {
    if (actionBusy) return;
    setActionBusy(true);
    setError('');
    setNotice('');
    try {
      await action();
    } catch (failure) {
      if (failure.status === 401) {
        signedOut();
        setStartupError('세션이 만료됐어요. 다시 로그인해 주세요.');
      } else setError(failure.message);
    } finally {
      setActionBusy(false);
    }
  }

  function changed(message) {
    setEditor(null);
    setNotice(message);
    setRevision(previous => previous + 1);
  }

  const addEvent = day => setEditor({ kind: 'event', date: day });
  const openEvent = id => run(async () => {
    const item = await call('/event/' + id);
    setEditor({ kind: 'event', item, date: item.startTime.slice(0, 10) });
  });
  const toggle = id => run(async () => {
    const item = await call('/event/' + id);
    await call('/event/' + id, { method: 'PUT', body: { ...item, isCompleted: !item.isCompleted } });
    changed(item.isCompleted ? '완료 표시를 취소했어요.' : '일정을 완료했어요.');
  });
  const openDiary = (day, entry) => run(async () => {
    const id = entry?.diaryId ?? entry?.id;
    const item = id ? await call('/diary/' + id) : undefined;
    setEditor({ kind: 'diary', item, date: day });
  });
  const openResult = item => {
    if (item.type === 'EVENT') openEvent(item.id);
    if (item.type === 'DIARY') openDiary(item.dateTime.slice(0, 10), item);
    if (item.type === 'LABEL') run(async () => setEditor({ kind: 'label', item: await call('/label/' + item.id) }));
  };
  const signOut = () => run(async () => {
    if (!demo) await request('/logout', { method: 'POST' });
    setStartupError('');
    signedOut();
  });
  const startDemo = async () => {
    const client = createDemo(sampleHome);
    setDemo(() => client);
    setHome(await client('/home'));
  };

  if (startup) return <main className="startup"><Brand /><p role="status">MOA를 여는 중이에요…</p></main>;
  if (!home) return <Auth onLogin={setHome} onDemo={startDemo} initialError={startupError} />;
  const diaryFor = day => diaries?.find(item => item.dateTime.startsWith(day));
  return <div className="app">
    <aside className="sidebar">
      <Brand />
      <nav aria-label="메인 메뉴">{navigation.map(([key, label, Icon]) => <button key={key} className={page === key ? 'selected' : ''} aria-current={page === key ? 'page' : undefined} onClick={() => go(key)}><Icon size={20} /><span>{label}</span></button>)}</nav>
      <div className="account"><div className="avatar">{home.nickname?.slice(0, 1) || 'M'}</div><div><strong>{home.nickname}</strong><small>{demo ? '예시 모드' : '내 계정'}</small></div><button className="icon" aria-label={demo ? '예시 모드 종료' : '로그아웃'} onClick={signOut}>{demo ? <ArrowLeft size={18} /> : <LogOut size={18} />}</button></div>
    </aside>
    <main className="content">
      <div className="topbar"><span>{navigation.find(([key]) => key === page)[1]}</span>
        {page !== 'search' && <button className="primary" onClick={() => {
          if (page === 'diary') openDiary(date, diaryFor(date));
          else addEvent(page === 'calendar' ? date : today());
        }} disabled={page === 'diary' && diaries === null}><Plus size={18} />{page === 'diary' ? '일기 쓰기' : '일정 추가'}</button>}
      </div>
      {demo && <p className="demo-banner"><Sparkles size={16} /> 예시 모드 · 변경 내용은 서버에 저장되지 않아요.</p>}
      <ErrorText message={error} />
      {notice && <p role="status" className="notice">{notice}</p>}
      {(loading || actionBusy) && <p className="loading" role="status">불러오는 중…</p>}
      {page === 'home' && <Dashboard home={home} go={go} openEvent={openEvent} openDiary={openDiary} toggle={toggle} />}
      {page === 'calendar' && <Calendar date={date} choose={setDate} events={events} add={addEvent} openEvent={openEvent} diary={diaryFor(date)} canWriteDiary={diaries !== null} openDiary={openDiary} />}
      {page === 'diary' && <section className="card page-card"><header><div className="section-icon peach"><BookOpen size={21} /></div><h2>일기 모아보기</h2><input aria-label="일기를 쓸 날짜" type="date" value={date} onChange={event => { if (event.target.value) setDate(event.target.value); }} /></header>{diaries && <Results items={diaries} open={openResult} />}</section>}
      {page === 'search' && <section className="card page-card"><header><div className="section-icon purple"><Search size={21} /></div><h2>기록 검색</h2></header><form className="search-form" onSubmit={event => { event.preventDefault(); const value = new FormData(event.currentTarget).get('keyword').trim(); if (value) { setKeyword(value); setRevision(previous => previous + 1); } }}><input name="keyword" aria-label="검색어" type="search" placeholder="일정, 일기, 라벨 검색" required defaultValue={keyword || ''} /><button className="primary">검색</button></form>{results && <Results items={results} open={openResult} />}</section>}
    </main>
    {editor && <Editor editor={editor} call={call} changed={changed} close={() => setEditor(null)} />}
  </div>;
}

createRoot(document.getElementById('root')).render(<App />);
