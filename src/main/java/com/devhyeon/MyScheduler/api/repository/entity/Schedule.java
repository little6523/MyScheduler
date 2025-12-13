package com.devhyeon.MyScheduler.api.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;

  @ManyToOne
  private User user;

  private String title;

  private String contents;

  private LocalDateTime scheduleDate;

  private String colorId;

  @Column(name = "created_at", insertable = false)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
