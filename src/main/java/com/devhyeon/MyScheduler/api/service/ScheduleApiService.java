package com.devhyeon.MyScheduler.api.service;

import com.devhyeon.MyScheduler.api.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleApiService {
  List<ScheduleDTO> getSchedulesByUserSeq(Long userSeq);

  ScheduleDTO addSchedule(ScheduleDTO scheduleDTO);
}
