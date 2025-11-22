package com.devhyeon.MyScheduler.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                    .loginProcessingUrl("/api/login")
                    .usernameParameter("id")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/myscheduler", true)
                    .permitAll()
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
}
