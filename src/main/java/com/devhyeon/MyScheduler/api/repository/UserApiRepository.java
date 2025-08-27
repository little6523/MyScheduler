package com.devhyeon.MyScheduler.api.repository;

import com.devhyeon.MyScheduler.api.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserApiRepository extends JpaRepository<User, Long> {
}
