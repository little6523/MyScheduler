package com.devhyeon.MyScheduler.security.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

  private final String[] permitUrlPatterns = {
          "/",
          "/myscheduler/login",
          "/myscheduler/signup",
          "/myscheduler",
          "/api/signup",
          "/api/login",
          "/css/**",
          "/html/**",
          "/js/**"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(permitUrlPatterns).permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/myscheduler/login")
//                    .loginProcessingUrl("/api/login")
//                    .usernameParameter("id")
//                    .passwordParameter("password")
//                    .defaultSuccessUrl("/myscheduler", true)
                    .permitAll()
//                    .successHandler((request, response, authentication) -> {
//                      writeSuccess(response, authentication.getName());
//                    })
//                    .failureHandler((request, response, exception) -> {
//                      String message = "아이디 또는 비밀번호가 올바르지 않습니다.";
//                      writeFailure(response, message);
//                    })
            )
            .logout(logout -> logout.permitAll())
            .csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    System.out.println("BCryptPasswordEncoder 호출!!");
    return new BCryptPasswordEncoder();
  }

  private void writeSuccess(HttpServletResponse response, String username) throws IOException {
    writeJson(response, HttpStatus.OK, """
            {
              "loginSuccessYn": "Y",
              "username": "%s"
            }
            """.formatted(username));
  }

  public static void writeFailure(HttpServletResponse response, String message) throws IOException {
    writeJson(response, HttpStatus.UNAUTHORIZED, """
            {
              "loginSuccessYn": "N",
              "message": "%s"
            }
            """.formatted(message));
  }

  private static void writeJson(HttpServletResponse response, HttpStatus status, String json) throws IOException {
    response.setStatus(status.value());
    response.setContentType("application/json");
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.getWriter().write(json);
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
