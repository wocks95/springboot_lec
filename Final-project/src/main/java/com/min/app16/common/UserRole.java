package com.min.app16.common;

/**
 * 회원 권한
 * 
 * ADMIN : 관리자
 * USER : 일반 회원
 */


public enum UserRole {
  
  ADMIN("ADMIN"), USER("USER"); // ADMIN 은 0이고, USER 는 1 입니다.
  
  private String userRole;
  
  UserRole(String userRole) {
    this.userRole = userRole;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  @Override
  public String toString() {
    return "UserRole[userRole=" + userRole + "]";  
  }
  
  
}
