package com.min.app15.controller;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.app15.model.dto.MenuDto;
import com.min.app15.model.exception.MenuNotFoundException;
import com.min.app15.model.message.ResponseErrorMessage;
import com.min.app15.model.message.ResponseMessage;
import com.min.app15.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MenuController {
  
  private final MenuService menuService;
  
  @GetMapping(value = "/categories", produces = "application/json")
  public ResponseMessage findCategoryList() {
     
    return ResponseMessage.builder()
                    .status(200)
                    .message("카테고리 목록 조회 성공")
                    .results(Map.of("categories", menuService.findByCategoryList()))
                    .build();
  }
  
  
  @PostMapping(value = "/menu", produces = "application/json")
  public ResponseMessage regist(@RequestBody MenuDto menuDto) {
    
    return ResponseMessage.builder()
                 .status(200)
                 .message("메뉴 등록 성공")
                 .results(Map.of("menu", menuService.registMenu(menuDto)))
                 .build();
    
  }
  @PutMapping(value = "/menu/{menuCode}", produces = "application/json")
  public ResponseMessage modify(
      @PathVariable(name = "menuCode") Integer menuCode
    , @RequestBody MenuDto menuDto) throws MenuNotFoundException {
    //여기서 던진 예외는 ExceptionHandler가 받아서 메시지를 확인해줍니다.
    menuDto.setMenuCode(menuCode);
    
    return ResponseMessage.builder()
                   .status(200)
                   .message("메뉴 수정 성공")
                   .results(Map.of("menu", menuService.modifyMenu(menuDto)))
                   .build();
  }
  
  @DeleteMapping(value = "/menu/{menuCode}", produces = "application/json")
  public ResponseMessage remove(@PathVariable(name = "menuCode") Integer menuCode) throws MenuNotFoundException {
    //여기서 던진 예외는 ExceptionHandler가 받아서 메시지를 확인해줍니다.
    menuService.deleteMenu(menuCode);
    
    return ResponseMessage.builder()
                    .status(200)
                    .message("메뉴 삭제 성공")
                    .results(null)
                    .build();
  }
  
  @GetMapping(value = "/menu/{menuCode}", produces = "application/json")
  public ResponseMessage findMenuById(@PathVariable(name = "menuCode") Integer menuCode) {
    return ResponseMessage.builder()
                   .status(200)
                   .message("메뉴 조회 성공")
                   .results(Map.of("menu", menuService.findMenuById(menuCode)))
                   .build();
  }
  
  @GetMapping(value = "/menu", produces = "application/json") //페이징이 있어야됨
  public ResponseMessage findMenuList(/*@PageableDefault*/ Pageable pageable) {
    
    /*
     * Pageable 인터페이스
     * 
     * 1. 페이징 처리에 필요한 정보(size, page, sort)를 처리하는 인터페이스입니다.
     * 2. Pageable 인터페이스의 정보를 초기화할 수 있습니다.
     *   1) @PageableDefault Annotation
     *   2) 프로퍼티에 등록 (application.properties)
     * 3. Pageable 인터페이스에 파라미터 전달하는 방법
     *   1) page : page=1
     *   2) size : size=10
     *   3) sort : sort=menuCode,desc 또는 sort=menuCode,asc
     * 4. 주의사항
     *   파라미터 page=1 로 전달되면 Pageable 인터페이스는 2페이지로 인식합니다.(시작 페이지가 0이기 때문입니다.)
     *   Pageable 인터페이스의 page 값은 -1 처리해야 합니다.
     */
    
    // Pageable 인터페이스의 page 값을 1 감소하는 코드
    pageable = pageable.withPage(pageable.getPageNumber() -1);
    
    // System.out.println(pageable);
    
    return ResponseMessage.builder()
                   .status(200)
                   .message("메뉴 조회 성공")
                   .results(Map.of("menuList", menuService.findMenuList(pageable)))
                   .build();
  }
  
  @GetMapping(value = "/menu/price/{menuPrice}", produces = "application/json")
  public ResponseMessage findByMenuPrice(@PathVariable(name = "menuPrice") Integer menuPrice) {
    return ResponseMessage.builder()
                   .status(200)
                   .message(menuPrice + "원 이상 메뉴 조회 성공")
                   .results(Map.of("menuPrice", menuService.findByMenuPrice(menuPrice)))
                   .build();
  }

  @GetMapping(value = "/menu/name/{menuName}", produces = "application/json")
  public ResponseMessage findByMenuName(@PathVariable(name = "menuName") String menuName) {
    return ResponseMessage.builder()
                   .status(200)
                   .message(menuName + "이 포함된 메뉴 조회 성공")
                   .results(Map.of("menuPrice", menuService.findByMenuName(menuName)))
                   .build();
  }
  
  
}
