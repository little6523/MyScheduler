package com.devhyeon.MyScheduler.api.dto;

import com.devhyeon.MyScheduler.api.repository.entity.Schedule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ScheduleDTO {

  private Long userSeq;

  private String title;

  private String contents;

  private LocalDateTime scheduleDate;

  private String colorId;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static ScheduleDTO fromEntity(Schedule schedule) {
    return new ScheduleDTO(
            schedule.getSeq(),
            schedule.getTitle(),
            schedule.getContents(),
            schedule.getScheduleDate(),
            schedule.getColorId(),
            schedule.getCreatedAt(),
            schedule.getUpdatedAt()
    );
  }
}
