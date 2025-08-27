package com.devhyeon.MyScheduler.api.service.serviceImpl;

import com.devhyeon.MyScheduler.api.dto.UserDTO;
import com.devhyeon.MyScheduler.api.repository.UserApiRepository;
import com.devhyeon.MyScheduler.api.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApiServiceImpl implements UserApiService {

  @Autowired
  private UserApiRepository userApiRepository;

  @Override
  public void signUp(UserDTO userDTO) {
    userApiRepository.save(userDTO.toEntity());
  }
}
