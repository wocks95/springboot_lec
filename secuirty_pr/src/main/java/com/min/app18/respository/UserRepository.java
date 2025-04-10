package com.min.app18.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.app18.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

  
  
}
