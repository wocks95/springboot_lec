package com.min.apptest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.apptest.dto.JoinDTO;
import com.min.apptest.service.JoinService;

@Controller
@ResponseBody
public class JoinController {

  private final JoinService joinService;
  
  public JoinController(JoinService joinService) {
    
    this.joinService = joinService;
  }
  
  
  @PostMapping("/join")
  public String joinProcess(JoinDTO joinDTO) {
    
    System.out.println(joinDTO.getUserEmail());
    joinService.joinProcess(joinDTO);
    
    return "ok";
  }
}
