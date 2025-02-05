package com.min.app07.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.min.app07.common.UserRole;

@Configuration
public class SecurityConfig {

  @Autowired
  private LoginFailureHandler loginFailureHandler; 
  
  
  /*
   *  SecurityFilterChain
   *  Spring Security 의 기본 애플리케이션 보안 구성을 담당합니다.
   *  사용자가 SecurityFilterChain 빈을 등록하면 Spring Security 의 보안 구성을 비활성화하고
   *  사용자가 직접 보안 구성을 정의할 수 있습니다.
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    
    // URL에 따라 서버 리소스에 접근 가능한 권한을 부여합니다.
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/", "/user/signup", "/auth/login").permitAll(); // requestMatchers 에 등록된 모든 주소는 누구나 접근할 수 있다. 
      auth.requestMatchers("/admin/**").hasAnyAuthority(UserRole.ADMIN.getUserRole()); // "/admin"으로 시작하는 주소는 "ADMIN" userRole을 가진 사용자가 접근할 수 있는 주소입니다.
      auth.anyRequest().authenticated();  // 모든 요청에 대해서 인증을 요구합니다. 이 설정이 포함되면 로그인 설정을 반드시 별도로 설정해서 처리해야 합니다.
    });
    
    // 로그인 설정을 처리합니다.
    http.formLogin(login -> {
      login.loginPage("/auth/login");            // "/auth/login" 요청 시 로그인 페이지로 이동합니다.
      login.usernameParameter("userId");         // 로그인 페이지의 유저 아이디가 가진 파라미터 이름을 지정합니다.
      login.passwordParameter("userPassword");   // 로그인 페이지의 유저 비밀번호가 가진 파라미터 이름을 지정합니다.
      login.defaultSuccessUrl("/", true);        // 로그인 성공하면 언제나(true) 메인("/")으로 이동합니다.
                                                 // 로그인 성공하면 세션에 인증 토큰이 저장됩니다.
      login.failureHandler(loginFailureHandler); // 로그인 실패하면 loginFailureHandler 가 동작합니다.
    });
    
    // 세션을 관리합니다.
    http.sessionManagement(session -> {
      session.maximumSessions(1);     // 하나의 세션을 사용합니다.
      session.invalidSessionUrl("/"); // 세션 만료 시 "/"로 이동합니다.
    });
    
    // 로그아웃을 관리합니다
    http.logout(logout -> {
      logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")); // "/auth/logout" 요청이 오면 로그아웃을 합니다.
      logout.invalidateHttpSession(true); // 로그아웃하면 세션 만료 처리합니다.
      logout.deleteCookies("JSESSIONID"); // 세션 아이디가 저장 되어 있는 JSESSIONID 쿠키 삭제합니다.
      logout.logoutSuccessUrl("/");       // 로그아웃하면 "/"로 이동합니다.
    });
    
    return http.build();
  }
  
  /*
   * PasswordEncoder
   * BCrypt 방식의 암호화 알고리즘을 지원하는 BCryptPasswordEncoder 빈을반환합니다.
   * BCrypt 암호화 알고리즘
   *  - 가장 많이 사용하는 비밀번호 해싱 알고리즘 중 하나
   *  - 높은 보안, 호환성(데이터베이스에 저장하기 쉬운 데이터생성), 신뢰성을가짐
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  /*
   * WebSecurityCustomizer
   * static 디렉터리 하위에 있는 모든 정적 리소스들에 대한 요청은 보안에서 제외합니다.
   */
  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }
  
  
  
  
}
