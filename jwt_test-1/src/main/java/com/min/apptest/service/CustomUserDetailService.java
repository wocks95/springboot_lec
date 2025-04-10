package com.min.apptest.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.min.apptest.dto.CustomUserDetails;
import com.min.apptest.entity.UserEntity;
import com.min.apptest.repository.UserRepository;

@Service
public class CustomUserDetailService  implements UserDetailsService {

  private final UserRepository userRepository;

  
  public CustomUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  
  @Override
  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
   
   
    
    // DB에서 조회
    UserEntity userData = userRepository.findByUserEmail(userEmail);
    
    if(userData != null) {
      //UserDetails에 담아서 return 하면 AutneticationManager가 검증 함
      return new CustomUserDetails(userData);
    }
    
    return null;
  }

  
  
}
