package com.min.app07.user.service;

import java.util.Map;

import com.min.app07.user.dto.LoginDto;
import com.min.app07.user.dto.SignupDto;

public interface IUserService {
  Map<String, String> signup(SignupDto signupDto);
  LoginDto findByUsername(String username);
  
}
