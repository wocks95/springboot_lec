package com.min.app18.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.min.app18.Dto.JoinDto;
import com.min.app18.entity.UserEntity;
import com.min.app18.respository.UserRepository;




@Service
public class JoinService {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public void joinProcess(JoinDto joinDto) {
    
    // db에 이미 동일한 username을 가진 회원이 존재하는지 검증해야한다. 
    
    
    UserEntity data = new UserEntity();
    
    data.setUsername(joinDto.getUsername());
    data.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
    data.setRole("ROLE_USER");
    
    userRepository.save(data);
    
  } 
}
