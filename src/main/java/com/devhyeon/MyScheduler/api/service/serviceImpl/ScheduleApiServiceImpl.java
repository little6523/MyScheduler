package com.devhyeon.MyScheduler.api.service.serviceImpl;

import com.devhyeon.MyScheduler.api.dto.ScheduleDTO;
import com.devhyeon.MyScheduler.api.repository.ScheduleApiRepository;
import com.devhyeon.MyScheduler.api.repository.entity.Schedule;
import com.devhyeon.MyScheduler.api.service.ScheduleApiService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleApiServiceImpl implements ScheduleApiService {

  private ScheduleApiRepository scheduleApiRepository;

  private ScheduleApiServiceImpl(ScheduleApiRepository scheduleApiRepository) {
    this.scheduleApiRepository = scheduleApiRepository;
  }

  @Override
  public List<ScheduleDTO> getSchedulesByUserSeq(Long userSeq) {

    List<Schedule> schedules = scheduleApiRepository.getSchedulesByUserSeq(userSeq);
    List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
    for (Schedule schedule : schedules) {
      ScheduleDTO scheduleDTO = ScheduleDTO.fromEntity(schedule);
      scheduleDTOList.add(scheduleDTO);
    }

    return scheduleDTOList;
  }
}
