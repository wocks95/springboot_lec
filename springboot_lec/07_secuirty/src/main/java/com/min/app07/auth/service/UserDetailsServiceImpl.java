package com.min.app07.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.min.app07.auth.model.CustomUserDetails;
import com.min.app07.user.dto.LoginDto;
import com.min.app07.user.service.IUserService;

/**
 * 사용자의 아이디를 전달 받아 해당 사용자의 정보를 UserDetails 타입으로 반환하는
 * loadUserByUsername() 메소드만 가지고 있는 UserDetailsService 인터페이스를 구현한 클래스입니다.
 * 
 * AuthenticationProvider
 *  - 인증 로직에서 UserDetailsService를 통해 사용자의 세부 정보(UserDetails)를 획득합니다.
 *  - 획득한 사용자 세부 정보와 사용자가 입력한 비밀번호의 일치 여부를 판단합니다. (PasswordEncoder 이용)
 *  - 비밀번호가 일치하면 AuthenticationFilter 에 이 정보(Authentication 객체)를 전달합니다. 이 정보는 SercurityContext가 저장된다.
 *  - 비밀번호가 일치하지 않으면 일반적으로 401 Unauthorized 를 반환합니다.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  
  @Autowired
  private IUserService userService;
  
  /**
   * AuthenticationProvider가 호출하는 메소드입니다.
   * @param username 사용자가 입력한 유저아이디입니다.
   * @return 데이터베이스에서 찾은 사용자의 정보를 담은 UserDetails 객체
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    // 데이터베이스에서 사용자 정보를 조회합니다.
    LoginDto loginDto = userService.findByUsername(username);
    
    // 사용자가 존재하지 않습니다.
    if(loginDto == null) {
      throw new UsernameNotFoundException("해당 회원 정보가 존재하지 않습니다.");
      
    }
    // 사용자가 존재하면 LoginDto 정보를 UserDetails 타입으로 반환합니다.
     CustomUserDetails customUserDetails = new CustomUserDetails();
     customUserDetails.setLoginDto(loginDto);
    return customUserDetails;
  }

}
