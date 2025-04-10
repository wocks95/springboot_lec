package com.min.app17.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.min.app17.model.dto.Message;
import com.min.app17.model.dto.RequestDto;
import com.min.app17.model.dto.ResponseDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ChatgptController {

  private final RestTemplate restTemplate;
  
  @GetMapping(value = "/chat/start", produces = "application/json")
  public ResponseDto start(HttpSession session, @RequestParam(name = "prompt") String prompt) throws Exception {
    
    String systemPrompt = "you are a thoughtful assistant. Answer in Korean.";
    
    List<Message> messages = (List<Message>)session.getAttribute("messages");
    if(messages == null) {
      messages = new ArrayList<>();
      messages.add(new Message("system", systemPrompt));
    } else {
      messages.add(new Message("user", prompt));
    }
    session.setAttribute("messages", messages);
    
    String model = "gpt-4o";
    RequestDto requestDto = new RequestDto(model, messages);
    System.out.println(requestDto);
    
    String url = "https://api.openai.com/v1/chat/completions";
    ResponseDto responseDto = restTemplate.postForObject(url, requestDto, ResponseDto.class);
    System.out.println(responseDto);
        
    return responseDto;
  }
  
  @GetMapping(value = "/chat/end")
  public String end(HttpSession session) {
    if(session.getAttribute("messages") != null)
      session.removeAttribute("messages");
    return "채팅서비스가 종료되었습니다.";
  }
  
}
