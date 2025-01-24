package com.min.app04.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.app04.dto.BookDto;

@Controller
public class AsyncController2 {

  @GetMapping("/index2")
  public void index2() {
    
  }
  
  /*
   * ResponseEntity 클래스
   * 1. 비동기 응답을 위한 클래스입니다.
   * 2. @ResponseBody 이 필요하지 않습니다. (자동으로 적용됩니다.)
   * 3.*/
  
  @GetMapping(value="/book/list")
  // @ResponseBody 
  public ResponseEntity<List<BookDto>> list() {
    
    List<BookDto> books = new ArrayList<>();
    books.add(new BookDto("소나기", "황순원"));
    books.add(new BookDto("홍길동전", "허균"));
    
    //응답 헤더 만들기
    HttpHeaders headers = new HttpHeaders();
    headers.add("content-Type", MediaType.APPLICATION_JSON_VALUE); // GetMappingdml 의 produces 를 대신하는 부분입니다.
    
    // 생성자를 이용한 ResponseEntity 객체 생성하기
    // new ResponseEntity<>(응답데이터, 응답헤더, 응답코드) 
    return new ResponseEntity<>(books, headers, HttpStatus.OK);
  }
  
  
  @GetMapping(value="/book/detail")
  public ResponseEntity<BookDto> detail(@RequestParam String word) {
 
    // 응답 데이터
    BookDto bookDto = new BookDto(word, "김작가");
    
    // 정적 메소드를 이용한 ResponserEntity 객체 생성
    return ResponseEntity.ok()
                         .header("content-Type", MediaType.APPLICATION_JSON_VALUE)
                         .body(bookDto);
  }
  
  @PostMapping("/book/regist")
  public ResponseEntity<Map<String, Object>> regist(@RequestBody BookDto bookDto) {
   try {
    if(bookDto.getTitle().isEmpty() || bookDto.getAuthor().isEmpty())
      throw new RuntimeException("서버 오류 발생");
    return ResponseEntity.ok(Map.of("status", 200, "registed", bookDto));
  } catch (Exception e) {
    e.printStackTrace();
    return ResponseEntity
              .internalServerError()
              .body(Map.of("status", 500, "message", e.getMessage()));
  }
  }
  
  
}
