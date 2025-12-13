package com.devhyeon.MyScheduler.api.service.serviceImpl;

import com.devhyeon.MyScheduler.api.dto.ScheduleDTO;
import com.devhyeon.MyScheduler.api.repository.ScheduleApiRepository;
import com.devhyeon.MyScheduler.api.repository.UserApiRepository;
import com.devhyeon.MyScheduler.api.repository.entity.Schedule;
import com.devhyeon.MyScheduler.api.repository.entity.User;
import com.devhyeon.MyScheduler.api.service.ScheduleApiService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleApiServiceImpl implements ScheduleApiService {

  private ScheduleApiRepository scheduleApiRepository;

  private UserApiRepository userApiRepository;

  private ScheduleApiServiceImpl(ScheduleApiRepository scheduleApiRepository, UserApiRepository userApiRepository) {
    this.scheduleApiRepository = scheduleApiRepository;
    this.userApiRepository = userApiRepository;
  }

  @Override
  public List<ScheduleDTO> getSchedulesByUserSeq(Long userSeq) {

    List<Schedule> schedules = scheduleApiRepository.getSchedulesByUserSeq(userSeq);
    List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
    for (Schedule schedule : schedules) {
      ScheduleDTO scheduleDTO = ScheduleDTO.builder().build();
      scheduleDTO = scheduleDTO.fromEntity(schedule);
      scheduleDTOList.add(scheduleDTO);
    }

    return scheduleDTOList;
  }

  @Override
  public ScheduleDTO addSchedule(ScheduleDTO scheduleDTO) {
    Long userSeq = scheduleDTO.getUserSeq();

    User user = userApiRepository.findById(userSeq)
            .orElseThrow(() -> new IllegalArgumentException("스케쥴 저장 중 에러... 유효하지 않은 유저 일련번호 입니다."));

    Schedule schedule = scheduleDTO.toEntity(user);
    scheduleApiRepository.save(schedule);

    return scheduleDTO;
  }
}
