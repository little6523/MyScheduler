package com.devhyeon.MyScheduler.api.repository.entity;

import com.devhyeon.MyScheduler.common.category.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @NonNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;

  @NonNull
  @Column(unique = true)
  private String id;

  @NonNull
  private String password;

  @Column(unique = true)
  private String email;

  @NonNull
  @Enumerated(EnumType.STRING)
  private Role role;

  public User(String id, String password, String email, Role role) {
    this.id = id;
    this.password = password;
    this.email = email;
    this.role = role;
  }
}
