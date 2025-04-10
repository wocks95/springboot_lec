package com.min.app15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.app15.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

  List<Category> findByCategoryCodeGreaterThan(Integer categoryCode);
}
