package com.min.app05.controller;


import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.app05.model.ResponseMessage;
import com.min.app05.model.dto.UserDto;
import com.min.app05.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

// REST API 서비스 개발을 위한 컨트롤러 : @Controller + @Responsebody
@RestController
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;
  
  // @Validated : 유효성 검사를 수행할 객체에 추가하는 애너테이션입니다.
  //              실제 유효성 검사는 해당 객체에서 수행합니다.
  @PostMapping("/users") // 사용자 등록을 의미하는 코드
  public ResponseMessage registUser(@Validated @RequestBody UserDto userDto) throws Exception { // 파라미터로 보내는것이 아닌 본문을 담아서 보낸다.
    return ResponseMessage.builder()
               .status(201) // 201 Created (요청이 성공적으로 처리되었으며, 자원이 생성되었음을 나타내는 성공 상태 응답 코드)
               .message("사용자 등록 성공")
               .results(Map.of("user", userService.registUser(userDto)))
               .build();
  }
  
  @PutMapping("/users/{userId}") // 수정
  public ResponseMessage modifyUser(@PathVariable int userId, @RequestBody UserDto userDto) throws Exception {
    userDto.setUserId(userId);
    return ResponseMessage.builder()
               .status(201)
               .message("사용자 정보 수정 성공")
               .results(Map.of("user", userService.modifyUser(userDto)))
               .build();
  }
  
  @DeleteMapping("/users/{userId}")
  public ResponseMessage removeUser(@PathVariable int userId) {
    return ResponseMessage.builder()
               .status(204) // 요청이 성공하였으나 해당 데이터를 참조할 수 없음을 의미합니다. 삭제 후 204를 사용할 수 있으나 주로 200을 사용합니다.
               .message("사용자 삭제 성공")
               .results(null)
               .build();
  }
  
  @GetMapping("/users")
  public ResponseMessage getUsers(HttpServletRequest request) throws Exception {
    return ResponseMessage.builder()
               .status(200)  // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송하였습니다.
               .message("사용자 목록 조회 성공")
               .results(Map.of("users", userService.getUsers(request)))
               .build();
  }
  
  @GetMapping("/users/{userId}")
  public ResponseMessage getUserById(@PathVariable int userId) throws Exception {
    return ResponseMessage.builder()
               .status(200) // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송하였습니다.
               .message("사용자 조회 성공")
               .results(Map.of("user", userService.getUserById(userId)))
               .build();
  }
  
  
}
