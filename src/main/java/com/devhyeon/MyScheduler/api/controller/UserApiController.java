package com.devhyeon.MyScheduler.api.controller;

import com.devhyeon.MyScheduler.api.dto.UserDTO;
import com.devhyeon.MyScheduler.api.service.UserApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {

  @Autowired
  private UserApiService userApiService;

  private final AuthenticationManager authenticationManager;

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDTO userDTO) {
    Map<String, Object> result = new HashMap<>();
    HttpHeaders headers = new HttpHeaders();

    userApiService.signUp(userDTO);

    result.put("result", "성공");

    return ResponseEntity.ok().headers(headers).body(result);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
    UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDTO.getId(), userDTO.getPassword());

    try {
      // 🔐 인증 시도
      Authentication authentication = authenticationManager.authenticate(authToken);

      // 인증 정보 SecurityContext에 저장 (세션 기반 로그인)
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // ✅ 로그인 성공 응답 (JSON)
      return ResponseEntity.ok(Map.of(
              "loginSuccessYn", "Y",
              "id", authentication.getName()
      ));
    } catch (Exception e) {
      // ❌ 로그인 실패 응답 (JSON)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(Map.of(
                      "loginSuccessYn", "N",
                      "message", "아이디 또는 비밀번호가 올바르지 않습니다."
              ));
    }
  }

}
