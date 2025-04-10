package com.min.app15.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.min.app15.model.entity.Menu;

/*
 * Spring Data JPA 의 Repository 인터페이스 구조
 * 
 *  Repository                  별도 기능 없음
 *      ↑
 *  CrudRespository             CRUD 기능 제공
 *      ↑
 *  PagingAndSortingRepository  페이징 기능 제공
 *      ↑ 
 *  JpaRepository               영속 컨텍스트 관련 일부 JPA 관련 추각 기능(예: 삭제)
 */

// JpaRepository<엔티티, 엔티티ID>
public interface MenuRepository extends JpaRepository<Menu, Integer> {

  List<Menu> findByMenuPriceGreaterThanEqual(Integer menuPrice);
  //List<Menu> findByMenuPriceGreaterThanEqual(Integer menuPrice, Sort sort);

  List<Menu> findByMenuNameContaining(String menuName);

  
  
}
