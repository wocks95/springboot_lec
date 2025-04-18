package com.min.apptest.jwt;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.min.apptest.dto.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// 토큰을 받아서 로그인 하는 곳
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  
  private final JwtUtil jwtUtil;

  public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
 
      this.authenticationManager = authenticationManager;
      this.jwtUtil = jwtUtil;
  }
  
  
  
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    
    //클라이언트 요청에서 username, password 추출
    String userEmail = obtainUsername(request);
    String userPw = obtainPassword(request);
    
    System.out.println(userEmail);
    
    //스프링 시큐리티에서 userEmail과 userPw를 검증하기 위해서는 token에 담아야 함
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, userPw, null);
 
    //token에 담은 검증을 위한 AuthenticationManager로 전달
    return authenticationManager.authenticate(authToken);
  }
  
  //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

   //UserDetails
   CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
    
   String userEmail = customUserDetails.getUsername();
    
   Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
   Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
   GrantedAuthority auth = iterator.next();

   String role = auth.getAuthority();

   String token = jwtUtil.createJwt(userEmail, role, 60*60*10L);

   response.addHeader("Authorization", "Bearer " + token);
   
   /*
   Authorization: 타입 인증토큰
  
   HTTP 인증 방식은 RFC 7235 저으이에 따라 아래 인증 헤더 형태를 가져야 한다.
   //예시
   Authorization: Bearer 인증토큰string
   */
  }
  
  
  //로그인 실패시 실행하는 메소드
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    
    //로그인 실패시 401 응답 코드 반환
    response.setStatus(401);
  }
  
}
