package com.devhyeon.MyScheduler.api.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Getter
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

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
