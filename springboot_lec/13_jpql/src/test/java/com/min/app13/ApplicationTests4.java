package com.min.app13;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app13.entity4.Menu;




@SpringBootTest
class ApplicationTests4 {
 //엔티티 매니저 팩토리 
 private static EntityManagerFactory entityManagerFactory;
 
 // 엔티티 매니저
 private EntityManager entityManager; 
 
 // 전체 테스트를 시작하기 전에 엔티티 매니저 팩토리를 생성합니다. (테스트 클래스가 동작하기 이전)
 @BeforeAll
 static void setEntityManagerFactory() throws Exception {
   entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
 }
 
 // 개별 테스트를 시작하기 전에 엔티티 매니저를 생성합니다. (테스트 메소드가 동작하기 이전)
 @BeforeEach
 void setEntityManager() throws Exception {
   entityManager = entityManagerFactory.createEntityManager();
 }
 
 // 전체 테스트가 종료되면 엔티티 매니저 팩토리를 소멸합니다. (테스트 클래스가 동작한 이후)
 @AfterAll
 static void closeEntityManagerFactory() throws Exception {
   entityManagerFactory.close();
 }
 
 // 개별 테스트가 종료될때마다 엔티티 매니저를 소멸합니다. (테스트 메소드가 동작한 이후)
 @AfterEach
 void closeEntityManager() throws Exception {
   entityManager.close();
 }

  @Test
  void 서브쿼리_select_test() {
    
    // JPQL 에서는 서브쿼리를 SELECT 절과 FROM 절에서는 사용할 수 없습니다.
    // JPQL 에서는 서브쿼리를 조건절(WHERE, HAVING)에서만 사용할 수 없습니다.
    
    // 문제. 카테고리 이름이 "커피"인 메뉴를 조회하기(카테고리 이름이 "커피"인 카테고리의 카테고리 코드 조회를 서브쿼리로 사용) 
    
    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT m FROM menu4 m");
    jpql.append(" WHERE m.categoryCode = ");
    jpql.append("(SELECT c.categoryCode FROM category4 c WHERE c.categoryName = '커피')");
    
    List<Menu> menuList = entityManager.createQuery(jpql.toString(), Menu.class)
                                       .getResultList();
    
    Assertions.assertThat(menuList.isEmpty()).isFalse();
    menuList.forEach(System.out::println);
  }
  @Test
  void 동적쿼리_select_test() {
    
    // 문제. 메뉴명에 '버터'가 포함되고, 카테고리 코드가 4인 메뉴를 조회합니다.
    // 단, 메뉴명과 카테고리 코드는 입력될 수도 있고 입력되지 않을 수도 있다고 가정합니다.
    
    String searchMenuName = "";
    int searchCategoryCode = 0;
    StringBuilder jpql = new StringBuilder("SELECT m FROM menu4 m ");
    if(searchMenuName != null && !searchMenuName.isEmpty() && searchCategoryCode > 0) {
      jpql.append("WHERE ");
      jpql.append("m.menuName LIKE '%' || :searchMenuName || '%' ");
      jpql.append("AND ");
      jpql.append("m.categoryCode = :searchCategoryCode");
    } else {
      if(searchMenuName != null && !searchMenuName.isEmpty()) {
        jpql.append("WHERE ");
        jpql.append("m.menuName LIKE '%' || :searchMenuName || '%' ");
      } else if(searchCategoryCode > 0) {
        jpql.append("WHERE ");
        jpql.append("m.categoryCode = :searchCategoryCode");        
        } 
      } 
    TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql.toString(), Menu.class);
    if(searchMenuName != null && !searchMenuName.isEmpty() && searchCategoryCode > 0) {
      typedQuery.setParameter("searchMenuName", searchMenuName)
                .setParameter("searchCategoryCode", searchCategoryCode);
    } else {
      if(searchMenuName != null && !searchMenuName.isEmpty()) {
        typedQuery.setParameter("searchMenuName", searchMenuName);
      } else if(searchCategoryCode > 0) {
        typedQuery.setParameter("searchCategoryCode", searchCategoryCode);
         }  
      }
    List<Menu> menuList = typedQuery.getResultList();
    
    Assertions.assertThat(menuList.isEmpty()).isFalse();
    menuList.forEach(System.out::println);
    
    
  }
  
 
}
