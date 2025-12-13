package com.devhyeon.MyScheduler.api.service.serviceImpl;

import com.devhyeon.MyScheduler.api.dto.UserDTO;
import com.devhyeon.MyScheduler.api.repository.UserApiRepository;
import com.devhyeon.MyScheduler.api.repository.entity.User;
import com.devhyeon.MyScheduler.api.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public Long getUserSeqByUserId(String userId) {
    User user = userApiRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유저 조회중... 유효한 유저가 존재하지 않습니다."));

    return user.getSeq();
  }
}
