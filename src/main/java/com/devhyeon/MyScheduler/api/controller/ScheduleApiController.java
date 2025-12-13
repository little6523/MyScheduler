package com.devhyeon.MyScheduler.api.controller;

import com.devhyeon.MyScheduler.api.dto.ScheduleDTO;
import com.devhyeon.MyScheduler.api.dto.UserDTO;
import com.devhyeon.MyScheduler.api.service.ScheduleApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleApiController {

  private ScheduleApiService scheduleApiService;

  public ScheduleApiController(ScheduleApiService scheduleApiService) {
    this.scheduleApiService = scheduleApiService;
  }

  @GetMapping("/schedules/{userSeq}")
  public ResponseEntity<List<ScheduleDTO>> getScheduleList(@PathVariable Long userSeq) {
    List<ScheduleDTO> schedules = scheduleApiService.getSchedulesByUserSeq(userSeq);
    return ResponseEntity.ok(schedules);
  }

  @PostMapping("/schedules")
  public ResponseEntity<ScheduleDTO> addSchedule(@RequestBody ScheduleDTO scheduleDTO) {
    ScheduleDTO addedScheduleDTO = scheduleApiService.addSchedule(scheduleDTO);
    return ResponseEntity.ok(addedScheduleDTO);
  }
}
