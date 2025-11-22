package com.devhyeon.MyScheduler.api.dto;

import com.devhyeon.MyScheduler.api.repository.entity.MyUser;
import com.devhyeon.MyScheduler.common.category.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDTO {

  private String id;
  private String password;
  private String email;
  private Role role = Role.user;

  public MyUser toEntity() {
    return new MyUser(
            this.id,
            this.password,
            this.email,
            this.role
    );
  }
}
