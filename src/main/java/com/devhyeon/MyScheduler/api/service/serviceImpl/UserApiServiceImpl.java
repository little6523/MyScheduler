package com.devhyeon.MyScheduler.api.service.serviceImpl;

import com.devhyeon.MyScheduler.api.dto.UserDTO;
import com.devhyeon.MyScheduler.api.repository.UserApiRepository;
import com.devhyeon.MyScheduler.api.repository.entity.User;
import com.devhyeon.MyScheduler.api.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserApiServiceImpl implements UserApiService {

  @Autowired
  private UserApiRepository userApiRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void signUp(UserDTO userDTO) {
    User user = new User(
            userDTO.getId(),
            passwordEncoder.encode(userDTO.getPassword()),
            userDTO.getEmail(),
            userDTO.getRole()
    );

    userApiRepository.save(user);
  }

  @Override
  public void login(UserDTO userDTO) {
    User user = userApiRepository.findById(userDTO.getId())
            .orElseThrow(() -> new UsernameNotFoundException("해당 ID를 가진 유저를 찾을 수 없습니다."));
  }
}
