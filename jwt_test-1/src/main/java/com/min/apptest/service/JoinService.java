package com.min.apptest.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.min.apptest.dto.JoinDTO;
import com.min.apptest.entity.UserEntity;
import com.min.apptest.repository.UserRepository;

@Service
public class JoinService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder  bCryptPasswordEncoder;
  
  public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }
  
  public void joinProcess(JoinDTO joinDTO) {
    
    String userEmail = joinDTO.getUserEmail();
    String userPw = joinDTO.getUserPw();
    
    Boolean isExist = userRepository.existsByuserEmail(userEmail);
    
    if(isExist) {
      
      
      return;
    }
    
    
    UserEntity data = new UserEntity();
    
    data.setUserEmail(userEmail);
    data.setUserPw(bCryptPasswordEncoder.encode(userPw));
    data.setRole("ROLE_ADMIN");
    
    userRepository.save(data);
    
  }
  
}
