package com.min.app07.auth.model;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.min.app07.user.dto.LoginDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 *  UserDetailsService 인터페이스를 구현한 UserDetailsServiceImpl 클래스에서 loadUserByUsername() 메소드가 반환하는 타입입니다.
 *  Spring Security 에서는 로그인 성공한 사용자 정보를 UserDetails 타입의 데이터로 반환해야 합니다.
 *  LoginDto가 가지고 있는 로그인 사용자 정보를 UserDetails 에 전달해서 처리해야 합니다.
 */
@NoArgsConstructor
@Getter
@Setter
public class CustomUserDetails implements UserDetails {

  // 직렬화를 위한 serialVersionUID 생성
  private static final long serialVersionUID = -2438055059145369936L;
  
  // LoginDto
  private LoginDto loginDto;
  

  /**
   * 사용자의 권한 목록을 반환합니다.
   * UsernamePasswordAuthenticationToken에 사용자의 권한 목록을 전달할 때 사용합니다.
   */
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    loginDto.getUserRoles().forEach(userRole -> authorities.add(() -> userRole));
    return authorities;
  }

  /**
   * 사용자의 비밀번호를 반환합니다.
   * UsernamePasswordAuthenticationToken과 사용자의 비밀번호를 비교할 때 사용합니다.'
   * 
   */
  @Override
  public String getPassword() {
    return loginDto.getUserPassword();
  }

  /**
   * 사용자의 아이디를 반환합니다.
   * UsernamePasswordAuthenticationToken과 사용자의 아이디를 비교할 때 사용합니다.
   */
  
  @Override
  public String getUsername() {
    return loginDto.getUserId();
  }

}
