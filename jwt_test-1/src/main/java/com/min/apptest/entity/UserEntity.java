package com.min.apptest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "tbl_user")
public class UserEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
//  @Column(name = "user_email")
  private String userEmail;
  
//  @Column(name = "user_pw")
  private String userPw;
  
  @Column(name = "role")
  private String role;
  
}
