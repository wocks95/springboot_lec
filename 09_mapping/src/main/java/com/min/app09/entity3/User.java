package com.min.app09.entity3;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 엔티티의 특징
 * 1. 디폴트 생성자는 필수입니다.
 * 2. final 클래스, interface, enum, inner class 클래스는 사용할 수 없습니다.
 * 3. 필드에 final 처리할 수 없습니다.
 */
@Entity(name = "user3") 
@Table(
    name = "tbl_user"    // 생성된 테이블 이름
  , schema = "db_boot9"  // 스키마(데이터베이스)
) 

@Access(AccessType.FIELD) // 클래스 레벨의 @Access 이므로 클래스의 모든 필드에 반영됩니다.
                          // AccessType.FIELD 는 필드에 직접 접근하는 방식을 의미합니다.
                          // 디폴트 설정입니다. (getter 를 통하지 않고 필드 값을 확인할 수 있습니다.)

public class User {
  
  @Id
  @Column(name = "user_id")
  private int userId;
  
  @Column(name = "user_email") 
  @Access(AccessType.PROPERTY)  // 해당 필드는 getter() 를 통해서 필드 값을 확인할 수 있습니다.
  private String userEmail;
  
  @Column(name = "user_password") 
  private String userPassword;
  
  @Column(name = "user_phone") 
  private String userPhone;
  
  @Column(name = "nickname")
  @Access(AccessType.PROPERTY) // 해당 필드는 getter() 를 통해서 필드 값을 확인할 수 있습니다.
  private String nickname;
  
  @Column(name = "address")
  
  private String address;
  
  @Column(name = "create_dt")
  private Date createDt;
  
  @Column(name = "user_role")
  // @Enumerated(EnumType.ORDINAL)  // Enum 타입의 값을 순서대로 정수로 매핑합니다. 디폴트 값입니다.
  @Enumerated(EnumType.STRING)      // Enum 타입의 값을 문자열로 매핑합니다.
  private UserRole userRole;


  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserEmail() {
    System.out.println("getUserEmail() 메소드 호출");
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getNickname() {
    
    return nickname + "님";
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getCreateDt() {
    return createDt;
  }

  public void setCreateDt(Date createDt) {
    this.createDt = createDt;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", userEmail=" + userEmail + ", userPassword=" + userPassword + ", userPhone="
        + userPhone + ", nickname=" + nickname + ", address=" + address + ", createDt=" + createDt + ", userRole="
        + userRole + "]";
  }
  
  
}
