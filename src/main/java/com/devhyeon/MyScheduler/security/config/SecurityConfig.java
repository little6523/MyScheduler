package com.devhyeon.MyScheduler.security.config;

import com.devhyeon.MyScheduler.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;

  private final String[] permitUrlPatterns = {
          "/",
          "/myscheduler/login",
          "/myscheduler/signup",
          "/myscheduler",
          "/api/signup",
          "/api/login",
          "/api/loginYn",
          "/css/**",
          "/html/**",
          "/js/**"
  };

  public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
    this.customUserDetailsService = customUserDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(permitUrlPatterns).permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/myscheduler/login")
                    .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider());

    return http.build();
  }

  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(customUserDetailsService); // DB 조회
    provider.setPasswordEncoder(passwordEncoder());          // BCrypt 비교
    return provider;
  }


  @Bean
  public SecurityContextRepository securityContextRepository() {
    return new HttpSessionSecurityContextRepository();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    System.out.println("BCryptPasswordEncoder 호출!!");
    return new BCryptPasswordEncoder();
  }
}
