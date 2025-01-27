package com.min.app05.controller.advice;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.min.app05.model.ResponseErrorMessage;
import com.min.app05.model.exception.UserNotFoundException;

  /*
   * @ControllerAdvice
   * 1. 애플리케이션 내의 모든 컨트롤러에서 발생하는 예외를 처리할 수 있습니다.
   * 2. Advice 이므로 AOP 기술이 적용된 Annotation 입니다.
   * 3. 컨트롤러에 포함되어야 할 예외 처리 코드를 따로 분리해 낼 수 있습니다.
   *   이로 인해 코드의 중복 문제를 해결하고, 서로의 관심사를 분리할 수 있습니다.
   */

@ControllerAdvice
public class ExceptionController {

  // email 칼럼의 UNIQUE 제약 조건에 위배되는 경우
  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException1(DuplicateKeyException e) {
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
                                          .code("00")
                                          .message("중복된 키 입력")
                                          .describe("기존 회원과 동일한 이메일이 입력되었습니다")
                                        .build();
    
    return ResponseEntity.badRequest().body(errorMessage);
    
  }
  
  // InsertUserDto / UpdateUserDto 에 설정한 유효성 검사를 통과하지 못한 경우
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException2(MethodArgumentNotValidException e) {
    
    String code = null;
    String message = null;
    String describe = null;
    
    BindingResult bindingResult = e.getBindingResult();
    
    if(bindingResult.hasErrors()) {
            
      // 코드(code)에 따른 code 와 message 설정
      switch (bindingResult.getFieldError().getCode()) {
      case "NotBlank": 
        code = "01";
        message = "필수 입력 값의 누락 또는 공백 입력";
        break;
      case "Size":
        code = "02";
        message = "입력 가능한 크기를 벗어난 값 입력";
        break;
      }

      // 예외 메시지 설명 (유효성 검사 설정 시 작성한 메시지)
      describe = bindingResult.getFieldError().getDefaultMessage();
      
    }
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
                                  .code(code)
                                  .message(message)
                                  .describe(describe)
                                  .build();
    return ResponseEntity.badRequest().body(errorMessage);                  
  }
  
  // 경로 변수로 전달된 값이 정수가 아닌 경우
  @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException3(MethodArgumentTypeMismatchException e) {
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
        .code("03")
        .message("경로 변수 오류")
        .describe("잘못된 타입의 데이터가 입력되었습니다")
      .build();
    
    return ResponseEntity.badRequest().body(errorMessage);
    
  }
  
  // 전달된 회원번호(userId)를 가진 회원이 존재하지 않는 경우
  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException4(UserNotFoundException e) {
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
        .code("404")
        .message(e.getMessage()) // UserServiceImpl에서 전달한 예외 메시지
        .describe("해당 회원 데이터를 찾을 수 없습니다.")
        .build();
    
    // 정적 메소드로 처리하기 적절한 코드가 없어 생성자를 이용해 객체를 생성합니다. (Not Found 처리)
    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }
  
  // 경로 변수에 값이 전달되지 않는 경우
  @ExceptionHandler(value = NoResourceFoundException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException5(NoResourceFoundException e) {
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
        .code("05")
        .message("경로 변수 누락")
        .describe("필요한 정보가 누락되었습니다.")
        .build();
    
    return ResponseEntity.badRequest().body(errorMessage);
  }
  
  // page, display 파라미터에 정수가 아닌 값을 전달한 경우
  @ExceptionHandler(value = NumberFormatException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException6(NumberFormatException e) {
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
        .code("06")
        .message(e.getMessage()) 
        .describe("잘못된 요청 파라미터입니다.")
        .build();
    
    return ResponseEntity.badRequest().body(errorMessage);
  }
  
  // sort 파라미터에 ASC / DESC 가 아닌 값을 전달한 경우
  @ExceptionHandler(value = BadSqlGrammarException.class)
  public ResponseEntity<ResponseErrorMessage> handleUserRegistException7(BadSqlGrammarException e) {
    
    ResponseErrorMessage errorMessage = ResponseErrorMessage.builder()
        .code("O7")
        .message("잘못된 쿼리문 실행")
        .describe("파라미터 sort의 값이 잘못 전달되었습니다.")
        .build();
    
    return ResponseEntity.badRequest().body(errorMessage);
  }
  
  
  
  
  
  
}
