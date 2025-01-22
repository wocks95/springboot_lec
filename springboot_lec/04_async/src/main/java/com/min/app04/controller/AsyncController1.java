package com.min.app04.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.min.app04.dto.BoardDto;
import com.min.app04.service.IBoardService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AsyncController1 {
  
  private final IBoardService boardService;
  
  // Springboot 프로젝트의 Spring Web 디펜던시에는
  // jackson-databind 라이브러리가 포함되어 있습니다. (Spring MVC Project 라면 직접 pom.xml에 추가해야 합니다.)
  // 이 라이브러리가 Java 데이터와 JSON 데이터의 상호 변환을 담당합니다. 
  
  @GetMapping(value={"/board/list", "/board/list.json"}
        //  , produces="application/json") // 이 메소드의 응답 데이터는 JSON 타입입니다.
            , produces=MediaType.APPLICATION_JSON_VALUE) // 위 주석 내용과 동일합니다.
  @ResponseBody // 반환 값을 요청한 곳으로 응답합니다. 페이지 이동은 없다.
  public Map<String, Object> list(HttpServletRequest request) {
    return boardService.getBoardList(request);
    
    // Map<String, Object> map = boardService.getBoardList(request);
    // return (Map<String, Object>) map.get("boardList");
  }
  
  // 응답 데이터를 XML 데이터로 제공할 수 있습니다.
  // jackson-dataformat-xml 디펜던시가 이 역할을 수행할 수 있습니다.
  // 별도로 pom.xml에 추가해야 합니다.
  
  @GetMapping(value="/board/list.xml"
       //   , produces="application/xml") // 이 메소드의 응답 데이터는 xml 입니다.
            , produces=MediaType.APPLICATION_XML_VALUE) // 위 주석 내용과 동일합니다.
  @ResponseBody //반환하는 곳으로 직접 응답합니다.
  public Map<String, Object> listXml(HttpServletRequest request) {
    return boardService.getBoardList(request);
  }
  
  @PostMapping(value="/board/regist"
             , produces=MediaType.APPLICATION_JSON_VALUE) 
  @ResponseBody
  public Map<String, Object> regist(BoardDto boardDto) {
    System.out.println(boardDto);
    return boardService.registBoard(boardDto);
  }
  
  @PostMapping(value="/board/singleUpload"
             , produces=MediaType.APPLICATION_JSON_VALUE) 
  @ResponseBody
  public Map<String, Object> singleUpload(MultipartHttpServletRequest multipartRequest) {
    System.out.println(multipartRequest.getParameter("nickname"));
    MultipartFile profile = multipartRequest.getFile("profile");
    System.out.println(profile.getOriginalFilename());
    return null;
  }
  
  @PostMapping(value="/board/multipleUpload"
             , produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> multipleUpload(MultipartHttpServletRequest multipartRequest) {
    System.out.println(multipartRequest.getParameter("notice"));
    List<MultipartFile> attaches = multipartRequest.getFiles("attaches");
    attaches.forEach(attach -> System.out.println(attach.getOriginalFilename()));
    return null;
  }
  
  @PostMapping(value="/board/addUser"
             , produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> addUser(@RequestBody Map<String, Object> map) { 
                                         // JSON 문자열 데이터가 요청 본문에 포함되어 있기 때문에 @RequestBody를 이용해서 해당 JSON 문자열을 받습니다.
                                         // 받을 때는 DTO 또는 Map 을 이용합니다.
                                         // HtppServletRequest, @RequestParam 으로 받을 수 없습니다.
    System.out.println(map);
    return null;
  }
  
  
}
