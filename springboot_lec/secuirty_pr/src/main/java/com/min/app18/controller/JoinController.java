package com.min.app18.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.min.app18.Dto.JoinDto;
import com.min.app18.service.JoinService;



@Controller
public class JoinController {

  @Autowired
  private  JoinService joinService;
  
  @GetMapping("/join")
  public String joinPage() {
    
    return "join";
  }
  
  @PostMapping("/joinProc")
  public String joinProcess(JoinDto joinDto) {
    
    System.out.println(joinDto.getUsername());
    
    joinService.joinProcess(joinDto);
    
    return "redirect:/login";
  }
  
}
