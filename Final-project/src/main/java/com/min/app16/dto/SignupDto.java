package com.min.app16.dto;


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
public class SignupDto {

  private Integer userId;
  private String userEmail;
  private String userPw;
  private String userName;
  private String userBirtdate;
  private String userPhone;
  private String userNickname;
  private String userRole;
  
}
