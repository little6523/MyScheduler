package com.devhyeon.MyScheduler.api.dto;

import com.devhyeon.MyScheduler.api.repository.entity.Schedule;
import com.devhyeon.MyScheduler.api.repository.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ScheduleDTO {

  private Long userSeq;

  private String title;

  private String contents;

  private String scheduleDate;

  private String colorId;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private LocalDateTime changeStringToLocalDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return LocalDateTime.parse(this.scheduleDate, formatter);
  }

  private String changeLocalDateTimeToString(LocalDateTime scheduleDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return scheduleDate.format(formatter);
  }

  public ScheduleDTO fromEntity(Schedule schedule) {
    return new ScheduleDTO(
            schedule.getSeq(),
            schedule.getTitle(),
            schedule.getContents(),
            changeLocalDateTimeToString(schedule.getScheduleDate()),
            schedule.getColorId(),
            schedule.getCreatedAt(),
            schedule.getUpdatedAt()
    );
  }

  public Schedule toEntity(User user) {
    return Schedule.builder()
            .user(user)
            .title(this.title)
            .contents(this.contents)
            .scheduleDate(changeStringToLocalDateTime())
            .colorId(this.colorId)
            .build();
  }
}
