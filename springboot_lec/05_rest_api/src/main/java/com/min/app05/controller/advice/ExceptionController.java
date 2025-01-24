package com.min.app05.controller.advice;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.min.app05.model.ErrorMessage;

import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class ExceptionController {
  
  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ErrorMessage> handleUserRegistException2(DuplicateKeyException e) {
    
    ErrorMessage errorMessage = ErrorMessage.builder()
                                          .code("ERROR_CODE_00000")
                                          .error(e.getMessage())
                                          .description("기존 회원과 동일한 이메일이 입력되었습니다.")
                                          .build();
    
    return ResponseEntity.badRequest().body(errorMessage);
  }
  
  
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage>  handleUserRegistException(MethodArgumentNotValidException e) {
    
    String code = null;
    String error = null;
    String description = null;
    
    BindingResult bindingResult = e.getBindingResult();
    if(bindingResult.hasErrors()) {
      // 에러 메시지 (유효성 검사 설정 시 작성한 메시지)
      error = bindingResult.getFieldError().getDefaultMessage();
      // 코드(code)에 따른 code 와 description 설정
      switch (bindingResult.getFieldError().getCode()) {
      case "NotBlank": 
        code = "ERROR_CODE_00001";
        description = "필수 입력값이 누락되거나 공백입니다.";
        break;
      case "Size":
        code = "ERROR_CODE_00002";
        description = "크기를 벗어난 값이 입력되었습니다.";
        break;
      }
    }
    ErrorMessage errorMessage = ErrorMessage.builder()
                                  .code(code)
                                  .error(error)
                                  .description(description)
                                  .build();
    return ResponseEntity
              .badRequest()
              .body(errorMessage);                  
  }
  
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorMessage> handleUserRegistException3(MethodArgumentTypeMismatchException e) {
     ErrorMessage errorMessage = ErrorMessage.builder()
                                       .code("ERROR_CODE_00003")
                                       .error(e.getMessage())
                                       .description("잘못된 데이터가 입력되었습니다.")
                                       .build();
     return ResponseEntity.badRequest().body(errorMessage);
  }
  
}
