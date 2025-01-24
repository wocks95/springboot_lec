package com.min.app04.controller;


import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.min.app04.util.MailSendUtil;

@Controller
public class AsyncController3 {

  @Autowired
  private MailSendUtil mailSendUtil; //평소에는 Service 클래스에 사용해야한다.
  
  @GetMapping("/index3")
  public void index3() { 
  }
 
  
  @GetMapping("/user/sendcode")
  public ResponseEntity<Map<String, Object>> sendcode(@RequestParam String email) {
    
    /*
     * commons-lang 2.6 디펜던시 추가가 필요합니다. (pom.xml)
     * 
     * RandomStringUtils.random(int count, boolean letters, boolean numbers)
     *   int count       : 몇 글자의 랜덤 스트링을 생성할 지 지정합니다.
     *   boolean letters : 문자를 사용하려면 true 아니면 false 를 지정합니다.
     *   boolean numbers : 숫자를 사용하려면 true 아니면 false 를 지정합니다.
     */
    String code = RandomStringUtils.random(6, true, true);
    
    mailSendUtil.sendMail(email
                        , "[앱에서보냅니다.]인증요청"
                        , "<div>인증코드는 <strong>" + code + "</strong>입니다.");
    
    return ResponseEntity.ok()
                         .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                         .body(Map.of("code", code));
  }
  
}
