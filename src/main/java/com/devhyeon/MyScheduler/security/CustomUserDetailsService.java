package com.devhyeon.MyScheduler.security;

import com.devhyeon.MyScheduler.api.repository.UserApiRepository;
import com.devhyeon.MyScheduler.api.repository.entity.MyUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserApiRepository userApiRepository;

  public CustomUserDetailsService(UserApiRepository userApiRepository) {
    this.userApiRepository = userApiRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    MyUser user = userApiRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getId())
            .password(user.getPassword())
            .roles(user.getRole().toString())
            .build();
  }
}
