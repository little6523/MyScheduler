package com.devhyeon.MyScheduler.security;

import com.devhyeon.MyScheduler.api.repository.UserApiRepository;
import com.devhyeon.MyScheduler.api.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserApiRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // username = login()에서 만든 authToken 의 첫 번째 파라미터 = userDTO.getId()
    User user = userRepository.findById(username) // 🔁 필요하면 findByLoginId 등으로 변경
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

    // 스프링 시큐리티에서 사용하는 UserDetails 객체로 변환
    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getId())          // 로그인에 사용할 아이디
            .password(user.getPassword())    // ❗ 이미 BCrypt로 암호화된 비밀번호
            .roles("user")                   // 권한 (필요하면 DB에서 꺼내서 세팅)
            .build();
  }
}
