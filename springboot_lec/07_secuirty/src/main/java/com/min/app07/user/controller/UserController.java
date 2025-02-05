package com.min.app07.user.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.min.app07.user.dto.SignupDto;
import com.min.app07.user.service.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
  
  private final IUserService userService;
  
  @GetMapping("/user/signup")
  public void signup() { } // 주소를 경로로 인식한다.
  
  
  @PostMapping("/user/signup") // 매핑 방식이 다르면 주소가 같아도 문제가 생기지 않는다.
  public String signup(SignupDto signDto, Model model) {
    Map<String, String> map = userService.signup(signDto);
    model.addAttribute("message", map.get("message"));
    return map.get("path");
  }
}
