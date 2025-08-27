package com.devhyeon.MyScheduler.api.controller;

import com.devhyeon.MyScheduler.api.dto.UserDTO;
import com.devhyeon.MyScheduler.api.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserApiController {

  @Autowired
  private UserApiService userApiService;

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> test(@RequestBody UserDTO userDTO) {
    Map<String, Object> result = new HashMap<>();
    HttpHeaders headers = new HttpHeaders();

    userApiService.signUp(userDTO);

    result.put("result", "성공");

    return ResponseEntity.ok().headers(headers).body(result);
  }
}
