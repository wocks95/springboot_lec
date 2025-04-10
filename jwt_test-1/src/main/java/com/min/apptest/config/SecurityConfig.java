package com.min.apptest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.min.apptest.jwt.JwtUtil;
import com.min.apptest.jwt.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
  private final AuthenticationConfiguration authenticationConfiguration;
  
  private final JwtUtil jwtUtil;
  
  public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil) {
    this.authenticationConfiguration = authenticationConfiguration;
    this.jwtUtil =  jwtUtil;
  }
  
  //AuthenticationManager Bean 등록
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

      return configuration.getAuthenticationManager();
  }
  
  @Bean
  BCryptPasswordEncoder bCryptPasswordEncoder() {

      return new BCryptPasswordEncoder();
  } 
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http 
            // csrf disable
            .csrf((auth) ->  auth.disable())
            
            // From 로그인 방식 disable
            .formLogin((auth) -> auth.disable())
            
            // http basic 인증 방식 disable
            .httpBasic((auth) -> auth.disable())
            
            // 경로별 인가 작업
            .authorizeHttpRequests((auth) -> auth
              .requestMatchers("/login", "/", "/join").permitAll()
              .requestMatchers("/admin").hasRole("ADMIN")
              .anyRequest().authenticated())
            
            .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
            
            // 세션 설정
            .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    
    return http.build();
  }
  
}
