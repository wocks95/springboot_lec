package com.min.app07.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Authcontroller {

  @GetMapping("/auth/login")
  public void login() {} // 로그인 페이지로 이동
  
  @GetMapping("/admin/page") // 나중에는 controller 를 따로 만들어서 사용할 것
  public void admin() {}
  
}
