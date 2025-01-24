package com.min.app05.model.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {
  
  private int userId;           
  // @NotNull(message = "이메일은 반드시 입력해야 합니다.") // null 체크만 가능하고 빈 문자열("")이나 공백 문자 체크는 불가능합니다.
  
  @NotBlank(message = "이메일은 반드시 입력해야 합니다.") // null, 빈 문자열(""), 공백문자(" ")체크 합니다.
  @Size(max = 100, message = "이메일의 최대 글자 수는 100자입니다.") 
  private String email;
  
  @Size(min = 4, max = 20, message = "비밀번호는 4 ~ 20자입니다.")
  private String pwd;

  @NotBlank(message = "닉네임은 반드시 입력해야 합니다.")
  @Size(max = 100, message = "닉네임의 최대 글자 수는 100자입니다.")
  private String nickname;
  
  // @Past : 현재보다 과거여야 합니다.
  // @Future : 현재보다 미래여야 합니다.
  private Timestamp createDt;
}
