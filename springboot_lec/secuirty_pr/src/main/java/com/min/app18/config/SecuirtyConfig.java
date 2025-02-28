package com.min.app18.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecuirtyConfig {
  
  /*
   * BCrypt 암호화 메소드
   * 사용자 인증(로그인)시 비밀번호에 대해 단방향 해시 암호화를 진행하여 저장되어 있는 비밀번호와 대조한다.
   * 따라서 회원가입시 비밀번호 항목에 대해서 암호화를 진행해야 한다.
   * 
   * 스프링 시큐리티는 암호화를 위해 BCrypt Password Encoder 를 제공하고 권장한다. 
   * 따라서 해당 클래스를 return 하는 메소드를 만들어 @Bean으로 등록하여 사용하면 된다.
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() { 
    
    return new BCryptPasswordEncoder();
  }
  
  /*
   *  SecurityFilterChain
   *  Spring Security 의 기본 애플리케이션 보안 구성을 담당합니다.
   *  사용자가 SecurityFilterChain 빈을 등록하면 Spring Security 의 보안 구성을 비활성화하고
   *  사용자가 직접 보안 구성을 정의할 수 있습니다.
   */
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    
    httpSecurity
        .authorizeHttpRequests((auth) -> auth
              .requestMatchers("/", "/login", "loginProc", "/join", "/joinProc").permitAll()   //누구든 접속이 가능하다.(비회원도 가능)
              .requestMatchers("/admin").hasRole("ADMIN")   // ADMIN으로 등록한 유저만 접속이 가능하다.
              .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
              .anyRequest().authenticated() // 모든 요청에 대해서 인증을 요구합니다. 이 설정이 포함되면 로그인 설정을 반드시 별도로 설정해서 처리해야 합니다.
        ); 
    httpSecurity
      .formLogin((auth) -> auth.loginPage("/login")
          .loginProcessingUrl("/loginProc")
          .permitAll()
      );  // admin 으로 접속하면 자동으로 로그인 화면이 나온다.
      
    httpSecurity.csrf((auth) -> auth.disable());
    
    return httpSecurity.build();
  }
  
  
  
}
