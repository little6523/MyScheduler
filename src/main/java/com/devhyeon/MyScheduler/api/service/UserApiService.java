package com.devhyeon.MyScheduler.api.service;

import com.devhyeon.MyScheduler.api.dto.UserDTO;

public interface UserApiService {
  void signUp(UserDTO userDTO);

  void login(UserDTO userDTO);
}
