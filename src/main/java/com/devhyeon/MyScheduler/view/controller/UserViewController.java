package com.devhyeon.MyScheduler.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myscheduler")
public class UserViewController {

  @GetMapping("/login")
  public String getUserLoginView() {
    return "forward:/html/login.html";
  }

  @GetMapping("/signup")
  public String getUserSignupView() {
    return "forward:/html/signup.html";
  }
}
