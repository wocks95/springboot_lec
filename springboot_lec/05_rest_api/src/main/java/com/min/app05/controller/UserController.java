package com.min.app05.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.min.app05.model.ResponseErrorMessage;
import com.min.app05.model.ResponseMessage;
import com.min.app05.model.dto.InsertUserDto;
import com.min.app05.model.dto.UpdateUserDto;
import com.min.app05.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


/*
 * Swagger 설정 Annotation
 * @Tag
 * @Operation
 * @ApiResponse
 * @Parameter
 */

@Tag(name = "API 목록", description = "회원 관리 API")

// REST API 서비스 개발을 위한 컨트롤러 : @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;
  
  // @Validated : 유효성 검사를 수행할 객체에 추가하는 애너테이션입니다.
  //              실제 유효성 검사는 해당 객체(InsertUserDto / UpdateUserDto)에서 수행합니다.
  
  @Operation(summary = "신규 회원 등록", description = "이메일, 비밀번호, 닉네임을 이용하는 신규 회원 등록 기능입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "신규 회원 등록 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    , @ApiResponse(responseCode = "00", description = "중복된 이메일을 이용한 회원 등록 시도", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "01", description = "필수 입력값이 누락", content = @Content(schema = @Schema(example = """
        {
          "code":"01",
          "error":"필수 입력 값 누락 또는 공백",
          "description": "필수 입력값이 누락되거나 공백입니다."
        }
        """)))
    , @ApiResponse(responseCode = "02", description = "크기를 벗어난 값", content = @Content(schema = @Schema(example = "{\r\n"
        + "  \"code\":\"02\",\r\n"
        + "  \"error\":\"크기를 벗어난 값을 입력\",\r\n"
        + "  \"description\":\"크기를 벗어난 값이 입력되었습니다.\"\r\n"
        + "}")))  
  })
  @PostMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE) // 사용자 등록을 의미하는 코드
  public ResponseMessage registUser(@Validated @RequestBody InsertUserDto insertUserDto)  { // 파라미터로 보내는것이 아닌 본문을 담아서 보낸다.
    return ResponseMessage.builder()
              .status(201)  // 201 Created (요청이 성공적으로 처리되었으며, 자원이 생성되었음을 나타내는 성공 상태 응답 코드)
                            // 200 OK 를 사용해도 무방합니다.
              .message("회원 등록 성공")
              .results(Map.of("user", userService.registUser(insertUserDto)))
              .build();
  }
  
  
  
  @Operation(summary = "회원 정보 수정", description = "비밀번호와 닉네임을 수정하는 회원 정보 수정 기능입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "회원 정보 수정 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    , @ApiResponse(responseCode = "01", description = "필수 입력 값 누락", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))  
    , @ApiResponse(responseCode = "02", description = "크기를 벗어난 값 입력", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))  
    , @ApiResponse(responseCode = "404", description = "회원 정보 없음", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "03", description = "잘못된 타입의 경로 변수 전달", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "05", description = "경로 변수 누락", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
  })

  @PutMapping(value="/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE) // 수정
  public ResponseMessage modifyUser(@PathVariable int userId, @Validated @RequestBody UpdateUserDto updateUserDto) throws Exception {
    updateUserDto.setUserId(userId);
    return ResponseMessage.builder()
              .status(201)  // 수정 또한 삽입과 동일한 응답 코드를 사용합니다.
                            // 200 OK 를 사용해도 무방합니다.
              .message("회원 정보 수정 성공")
              .results(Map.of("user", userService.modifyUser(updateUserDto)))
              .build();
  }
  
  
  
  @Operation(summary = "회원 정보 삭제", description = "회원번호가 일치하는 회원의 정보 삭제 기능입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "회원 정보 삭제 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    , @ApiResponse(responseCode = "03", description = "잘못된 데이터 입력", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))  
    , @ApiResponse(responseCode = "404", description = "해당 회원 데이터를 찾을 수 가 없음", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "05", description = "경로 변수 누락", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))  
  })
  @DeleteMapping(value="/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseMessage removeUser(@PathVariable int userId) throws Exception {
    userService.removeUser(userId);
    return ResponseMessage.builder()
               .status(204) // 요청이 성공하였으나 해당 데이터를 참조할 수 없음을 의미합니다.
                            // 삭제 후 204를 사용할 수 있으나 실제로는 주로 200을 사용합니다.
               .message("회원 삭제 성공")
               .results(null)
               .build();
  }
  
  @Operation(summary = "회원 목록 조회", description = "page, display, sort 에 따른 회원 목록을 조회하는 기능입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    , @ApiResponse(responseCode = "06", description = "잘못된 타입의 파라미터 전달", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "07", description = "잘못된  파라미터 값 전달", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
  })
  @Parameters(value = {
      @Parameter(name = "page", required = true, description = "조회할 페이지 번호")
    , @Parameter(name = "display", required = true, description = "페이지에 포함할 회원 수")
    , @Parameter(name = "sort", required = true, description = "회원 정렬 방식") 
  })
  @GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseMessage getUsers(HttpServletRequest request) {
    return ResponseMessage.builder()
              .status(200)  // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송하였습니다.
              .message("회원 목록 조회 성공")
              .results(Map.of("users", userService.getUsers(request)))
            .build();
  }
  
  
  
  @Operation(summary = "회원 상세 조회", description = "회원 번호에 따른 회원 상세 정보를 조회하는 기능입니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "회원 조회 성공", content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    , @ApiResponse(responseCode = "404", description = "회원 정보 없음", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "03", description = "잘못된 타입의 경로 변수 전달", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
    , @ApiResponse(responseCode = "05", description = "경로 변수 누락", content = @Content(schema = @Schema(implementation = ResponseErrorMessage.class)))
  })
  @GetMapping(value="/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseMessage getUserById(@PathVariable int userId) throws Exception {
    return ResponseMessage.builder()
               .status(200) // 요청이 성공하여 가져온 리소스를 메시지 본문으로 전송하였습니다.
               .message("회원 조회 성공")
               .results(Map.of("user", userService.getUserById(userId)))
               .build();
  }
  
  
}
