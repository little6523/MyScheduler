package com.devhyeon.MyScheduler.api.controller;

import com.devhyeon.MyScheduler.api.dto.UserDTO;
import com.devhyeon.MyScheduler.api.service.UserApiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {

  @Autowired
  private UserApiService userApiService;

  private final AuthenticationConfiguration authenticationConfiguration;

  private final SecurityContextRepository securityContextRepository;

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDTO userDTO) {
    Map<String, Object> result = new HashMap<>();
    HttpHeaders headers = new HttpHeaders();

    userApiService.signUp(userDTO);

    result.put("result", "성공");

    return ResponseEntity.ok().headers(headers).body(result);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO
          , HttpServletRequest request, HttpServletResponse response) {
    UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDTO.getId(), userDTO.getPassword());

    try {
      AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

      // 🔐 인증 시도
      Authentication authentication = authenticationManager.authenticate(authToken);

      // 2) SecurityContext 생성 + Authentication 설정
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authentication);
      SecurityContextHolder.setContext(context);

      // 3) 🔥 세션에 SecurityContext 저장
      securityContextRepository.saveContext(context, request, response);

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

  @GetMapping("/loginYn")
  public ResponseEntity<?> loginInfo() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
      return ResponseEntity.ok(Map.of("authenticated", false));
    }

    return ResponseEntity.ok(Map.of(
            "authenticated", true,
            "username", auth.getName()
    ));
  }

}
