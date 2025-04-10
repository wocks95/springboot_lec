package com.min.app16.dto;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.min.app16.common.UserRole;

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
public class LoginDto {

  private Integer       userId;
  private String        userEmail;
  private String        userPw;
  private String        userName;
  private String        userBirthdate;
  private String        userPhone;
  private String        userNickname;
  private String        profileImg;
  private String        profileImgOriName;
  private String        profileImgSysName;
  private String        sessionId;
  private UserRole      userRole;
  private LocalDateTime createDt;
  private LocalDateTime changeDt;

  public List<String> getUserRoles() {
    if(userRole != null && userRole.getUserRole() != null && !userRole.getUserRole().isEmpty()) {
        return Arrays.asList(userRole.getUserRole().split(","));            
    }
    return new ArrayList<>(); // 없으면 빈 ArrayList<>
  }
  
}


