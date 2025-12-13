package com.devhyeon.MyScheduler.api.repository;

import com.devhyeon.MyScheduler.api.repository.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleApiRepository extends JpaRepository<Schedule, Long> {
  List<Schedule> getSchedulesByUserSeq(Long userSeq);
}
