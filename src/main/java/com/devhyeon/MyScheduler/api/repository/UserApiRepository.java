package com.devhyeon.MyScheduler.api.repository;

import com.devhyeon.MyScheduler.api.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserApiRepository extends JpaRepository<User, Long> {
//  Optional<User> findByUsername(String username);

  Optional<User> findById(String id);
}
