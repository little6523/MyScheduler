fetch("/api/loginYn")
    .then(res => res.json())
    .then(data => {
        if (data.authenticated) {
            // 로그인 상태
            document.getElementById("welcome").innerHTML ="<b>" + data.username + "</b>님 환영합니다!";
            document.getElementById("welcome").style.display = "block";
            document.getElementById("login-area").style.display = "none";
        } else {
            // 비로그인 상태
            document.getElementById("welcome").style.display = "none";
            document.getElementById("login-area").style.display = "block";
        }
    });