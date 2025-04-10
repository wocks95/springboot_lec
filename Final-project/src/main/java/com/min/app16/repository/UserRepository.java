package com.min.app16.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.min.app16.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByUserEmail(String userEmail);
}
