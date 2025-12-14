package com.devhyeon.MyScheduler.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myscheduler")
public class ScheduleViewController {

  @GetMapping("/schedule")
  public String getScheduleManageView() {
    return "forward:/html/schedule.html";
  }
}
