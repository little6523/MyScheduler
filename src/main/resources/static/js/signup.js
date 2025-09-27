(() => {
  const form = document.getElementById('signupForm');
  const submitBtn = document.getElementById('submitBtn');
  const userId = document.getElementById('userId');
  const password = document.getElementById('password');
  const email = document.getElementById('email');
  const userIdStatus = document.getElementById('userIdStatus');
  const passwordStatus = document.getElementById('passwordStatus');
  const emailStatus = document.getElementById('emailStatus');

  if (!form) return;

  // 간단한 클라이언트 검증 규칙
  const idRegex = /^[a-zA-Z0-9_\-]{4,16}$/; // 4~16자, 영문/숫자/언더스코어/하이픈
  const pwRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,16}$/;
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  // 모의 중복 데이터 (화면 전용)
  const existingIds = new Set(['admin', 'testuser']);
  const existingEmails = new Set(['test@example.com']);

  function setStatus(el, statusEl, ok, message) {
    el.classList.remove('valid', 'invalid');
    statusEl.classList.remove('ok', 'warn', 'err');
    if (ok === true) {
      el.classList.add('valid');
      statusEl.classList.add('ok');
    } else if (ok === false) {
      el.classList.add('invalid');
      statusEl.classList.add('err');
    }
    statusEl.textContent = message;
  }

  function validateId() {
    const value = userId.value.trim();
    if (!idRegex.test(value)) {
      setStatus(userId, userIdStatus, false, '4~16자 영문/숫자/_/-');
      return false;
    }
    if (existingIds.has(value)) {
      setStatus(userId, userIdStatus, false, '이미 사용 중');
      return false;
    }
    setStatus(userId, userIdStatus, true, '사용 가능');
    return true;
  }

  function validatePassword() {
    const value = password.value;
    if (!pwRegex.test(value)) {
      setStatus(password, passwordStatus, false, '규칙 불만족');
      return false;
    }
    setStatus(password, passwordStatus, true, '안전한 비밀번호');
    return true;
  }

  function validateEmail() {
    const value = email.value.trim();
    if (!emailRegex.test(value)) {
      setStatus(email, emailStatus, false, '형식 확인 필요');
      return false;
    }
    if (existingEmails.has(value.toLowerCase())) {
      setStatus(email, emailStatus, false, '이미 사용 중');
      return false;
    }
    setStatus(email, emailStatus, true, '사용 가능');
    return true;
  }

  function updateSubmit() {
    const valid = validateId() & validatePassword() & validateEmail(); // 모두 평가 위해 & 사용
    submitBtn.disabled = !Boolean(valid);
  }

  // 이벤트 바인딩
  userId.addEventListener('input', () => {
    validateId();
    updateSubmit();
  });
  password.addEventListener('input', () => {
    validatePassword();
    updateSubmit();
  });
  email.addEventListener('input', () => {
    validateEmail();
    updateSubmit();
  });

  form.addEventListener('submit', (e) => {
    e.preventDefault();
    updateSubmit();
    if (submitBtn.disabled) return;

    fetch("/api/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: userId.value,
        password: password.value,
        email: email.value,
      }),
    }).then((response) => console.log(response));

    alert('회원가입 성공!');
  });

  // 초기 상태 반영
  updateSubmit();
})();


