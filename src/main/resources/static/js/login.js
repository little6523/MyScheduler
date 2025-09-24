(() => {
  const form = document.getElementById('loginForm');
  const submitBtn = document.getElementById('loginSubmitBtn');
  const loginId = document.getElementById('loginId');
  const loginPassword = document.getElementById('loginPassword');
  const idStatus = document.getElementById('loginIdStatus');
  const pwStatus = document.getElementById('loginPasswordStatus');

  if (!form) return;

  function setStatus(el, statusEl, ok, message) {
    el.classList.remove('valid', 'invalid');
    statusEl.classList.remove('ok', 'err');
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
    const value = loginId.value.trim();
    if (value.length === 0) {
      setStatus(loginId, idStatus, false, '필수 입력');
      return false;
    }
    setStatus(loginId, idStatus, true, '확인됨');
    return true;
  }

  function validatePassword() {
    const value = loginPassword.value;
    if (value.length === 0) {
      setStatus(loginPassword, pwStatus, false, '필수 입력');
      return false;
    }
    setStatus(loginPassword, pwStatus, true, '확인됨');
    return true;
  }

  function updateSubmit() {
    const valid = validateId() & validatePassword();
    submitBtn.disabled = !Boolean(valid);
  }

  loginId.addEventListener('input', () => { validateId(); updateSubmit(); });
  loginPassword.addEventListener('input', () => { validatePassword(); updateSubmit(); });

  form.addEventListener('submit', (e) => {
    e.preventDefault();
    updateSubmit();
    if (submitBtn.disabled) return;
    alert('화면 데모용: 로그인 입력 유효성 통과. 실제 인증은 연결되지 않았습니다.');
  });

  updateSubmit();
})();


