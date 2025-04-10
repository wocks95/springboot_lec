package com.min.apptest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.apptest.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

  Boolean existsByuserEmail(String userEmail);
  
  UserEntity findByUserEmail(String userEmail);
  
  
}
