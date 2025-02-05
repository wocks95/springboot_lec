package com.min.app07.common;

// 열거형 - 순서대로 적어서 상수 처리 ADMIN == 0, USER == 1
public enum UserRole {
  ADMIN("ADMIN"), USER("USER");
  
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
