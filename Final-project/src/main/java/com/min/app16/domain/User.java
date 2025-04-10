package com.min.app16.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity(name = "user")
@Table(name = "tbl_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer userId;
  
  @Column(name = "user_email", unique = true, nullable = false)
  private String  userEmail;

  @Column(name = "user_pw", nullable = false)
  private String userPw;

  @Column(name = "user_name", nullable = false)
  private String userName;

  @Column(name = "user_birthdate", nullable = false)
  private String userBirthdate;

  @Column(name = "user_phone", nullable = false)
  private String userPhone;

  @Column(name = "user_nickname", nullable = false)
  private String userNickname;

  @Column(name = "profile_img", nullable = false)
  private String profileImg;

  @Column(name = "profile_img_ori_name")
  private String profileImgOriName;

  @Column(name = "profile_img_sys_name")
  private String profileImgSysName;

  @Column(name = "session_id", nullable = false)
  private String sessionId;

  @Column(name = "user_role")
  private String userRole;

  @Column(name = "create_dt")
  private LocalDateTime createDt;

  @Column(name = "change_dt")
  private LocalDateTime changeDt;
 }



