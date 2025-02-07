package com.min.app09.entity2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/*
 * 엔티티의 특징
 * 1. 디폴트 생성자는 필수입니다.
 * 2. final 클래스, interface, enum, inner class 클래스는 사용할 수 없습니다.
 * 3. 필드에 final 처리할 수 없습니다.
 */
// @Entity(name = "user2") 
@Table(
    name = "tbl_user"    // 생성된 테이블 이름
  , schema = "db_boot9"  // 스키마(데이터베이스)
  , uniqueConstraints = { @UniqueConstraint(columnNames = { "user_phone", "create_dt" }) } // 두 개 이상의 칼럼을 묶어서 Unique 제약 조건을 지정하는 경우에 필요합니다.
) 

@TableGenerator(
    name = "user_seq_table_generator"
  , table = "user_seq"                  // 시퀀스 테이블의 이름
  
    /* 아래 속성에서 사용한 값들은 모두 디폴트 값입니다.*/
  , pkColumnName = "sequence_name"      // 기본키 칼럼의 이름
  , pkColumnValue = "user_seq"          // 기본키 칼럼의 값
  
  , valueColumnName = "next_val"        // 일반 칼럼의 이름
  , initialValue = 0                    // 초기값
  , allocationSize = 1                  // 증가값
)

/*
  CREATE TABLE user_seq
  (
    sequence_name VARCHAR(255) NOT NULL,
    next_val      BIGINT,
    PRIMARY KEY (sequence_name)
  ) Engine=InnoDB;
 */
public class User {
  
  @Id
  @Column(name = "user_id")
  @GeneratedValue(
    // strategy = GenerationType.IDENTITY     // 기본키 생성을 데이터베이스에 위임합니다. (MYSQL의 AUTO_INCREMENT)
    // strategy = GenerationType.AUTO         // 기본키 생성 방식을 데이터베이스에 따라서 자동으로 선택합니다. (MYSQL의 경우 시퀀스 테이블, Oracle 의 경우 SEQUENCE)
    // strategy = GenerationType.SEQUENCE     // 시퀀스 데이터베이스 객체를 이용합니다. (Oracle)
      
       strategy = GenerationType.TABLE        // 키값을 생성하는 별도의 테이블을 이용합니다. 
    ,  generator = "user_seq_table_generator" // @TableGenrator 를 이용해서 키 값을 생성하는 별도의 테이블을 만듭니다.  
) 
  private int userId;
  
  @Column(name = "user_email", unique = true) // 중복된 값을 가질 수 없습니다. 디폴트는 false 입니다.
  private String userEmail;
  
  @Column(name = "user_password", nullable = false) // null 값을 가질 수 없습니다. (NOT NULL) 디폴트는 true 입니다.
  private String userPassword;
  
  @Column(name = "user_phone", columnDefinition = "VARCHAR(13) DEFAULT '010-0000-0000'") // 직접 해당 칼럼의 DDL 을 작성합니다. 
  private String userPhone;
  
  @Column(name = "nickname", unique = true) // 중복된 값을 가질 수 없습니다. 디폴트는 false 입니다.
  private String nickname;
  
  @Column(name = "address")
  @Transient // 테이블을 생성할 때 무시되는 칼럼입니다.
  private String address;
  
  @Column(name = "create_dt")
  @Temporal(TemporalType.TIMESTAMP) // 칼럼 타입이 DATETIME
  //@Temporal(TemporalType.DATE)       칼럼 타입이 DATE
  //@Temporal(TemporalType.TIME)       칼럼 타입이 TIME
  private Date createDt;
  
  @Column(name = "user_role", length = 100) // 최대 문자열의 길이가 100 입니다. 오직 String 타입에서만 사용할 수 있습니다. 디폴트는 255 입니다.
  private String userRole;


  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserEmail() {
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
    return nickname;
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

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", userEmail=" + userEmail + ", userPassword=" + userPassword + ", userPhone="
        + userPhone + ", nickname=" + nickname + ", address=" + address + ", createDt=" + createDt + ", userRole="
        + userRole + "]";
  }
  
  
}
