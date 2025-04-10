package com.min.app05.model.dto;

import java.sql.Timestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Swagger 설정 Annotation
 * @Schema
 */

@Schema(description = "회원 정보 수정 DTO")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateUserDto {

  /*
   * 유효성 검사를 위해서는 spring-boot-starter-validation 디펜던시가 필요합니다.
   */ 
  
  /*
   * @NotNull  : null 체크 가능 / 빈 문자열("")과 공백 문자(" ") 체크 불가능
   * @NotEmpty : null, 빈 문자열("") 체크 가능 / 공백 문자(" ") 체크 불가능
   * @NotBlank : null, 빈 문자열("")과 공백 문자(" ") 모두 체크 가능
   * @Size     : 문자열의 글자 수 제한
   * 
   * @Positive       : 양수 가능
   * @PositiveOrZero : 양수와 0만 허용
   * @Negative       : 음수만 허용
   * @NegativeOrZero : 음수와 0만 허용
   * @Min(?)         : 최소값 제한
   * @Max(?)         : 최대값 제한
   * 
   * @Past   : 현재보다 과거
   * @Future : 현재보다 미래
   * 
   * @Email              : 이메일만 가능 (기본 제공 형식)
   * @Pattern(regexp="") : 정규식 지정 
   */
  @Schema(description = "회원번호")
  private int userId;
  
  @Schema(description = "이메일", nullable = false, example = "id@example.com")
  private String email;
  
  @Schema(description = "비밀번호")
  @Size(min = 4, max = 20, message = "비밀번호는 4 ~ 20자입니다.")
  private String pwd;
  
  @Schema(description = "닉네임", nullable = false)
  @NotBlank(message = "닉네임은 반드시 입력해야합니다.")
  @Size(max = 100, message = "닉네임의 최대 글자 수는 100자입니다.")
  private String nickname;
  
  @Schema(description = "회원가입일시", example = "yyyy-MM-dd HH:mm:ss")
  private Timestamp createDt;
}
