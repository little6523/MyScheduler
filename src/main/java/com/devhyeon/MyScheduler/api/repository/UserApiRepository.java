package com.devhyeon.MyScheduler.api.repository;

import com.devhyeon.MyScheduler.api.repository.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserApiRepository extends JpaRepository<MyUser, Long> {

  Optional<MyUser> findById(String id);
}
