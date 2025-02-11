package com.min.app13;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app13.entity1.Menu;

@SpringBootTest
class ApplicationTests1 {
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
	void TypedQuery_단일_메뉴명_select_test() {
	  
	  String jpql = "SELECT m.menuName FROM menu1 m WHERE m.menuCode = 7";
	  
	  TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
	  
	  String menuName = typedQuery.getSingleResult();
	  
	  Assertions.assertThat(menuName).isEqualTo("민트미역국");
	  
	}

	@Test
	void Query_단일_메뉴명_select_test() {
	  
	  String jpql = "SELECT m.menuName FROM menu1 m WHERE m.menuCode = 7";
	  
	  Query query = entityManager.createQuery(jpql);
	  
	  Object menuName = query.getSingleResult();
	  
	  Assertions.assertThat(menuName).isEqualTo("민트미역국");
	}
	
	
	@Test
	void TypeQuery_다중행_select_test() {
	  
	  String jpql = "SELECT m FROM menu1 m";
	  
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql, Menu.class);
	  
	  List<Menu> menuList = typedQuery.getResultList();
	  
	  menuList.forEach(System.out::println);
	  
	}
	
	@Test
	void Query_다중_행_select_test() {
	  String jpql = "SELECT m FROM menu1 m";
	  
	  Query query = entityManager.createQuery(jpql);
	  
	  List<Menu> menuList = query.getResultList();
	  
	  menuList.forEach(System.out::println);
	}
	
	// 카테고리 코드 조회하기 (중복 제거)
	@Test
	void 카테고리_코드_test() {
	  String jpql = "SELECT DISTINCT m.categoryCode FROM menu1 m";
	  
	  TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);
	  List<Integer> categoryList = typedQuery.getResultList();
	  
	  assertNotNull(categoryList);
	  categoryList.forEach(System.out::println);
	}
	// 카테고리 코드가 6 또는 10 인 메뉴 조회하기
 @Test
 void 카테고리_6_또는_10_test() {
   String jpql = "SELECT c FROM menu1 c WHERE c.categoryCode IN (6, 10)";
   Query query = entityManager.createQuery(jpql);
   List<Menu> menuList = query.getResultList();
   
   assertNotNull(menuList);
   menuList.forEach(System.out::println);
 }

	// 메뉴명에 '마늘'이 포함되는 메뉴 조회하기
 @Test
 void Like_연산자_select_test() {
   String jpql = "SELECT m FROM menu1 m WHERE menuName LIKE '%마늘%'";
   Query query = entityManager.createQuery(jpql);
   List<Menu> menuList = query.getResultList();
   
   assertNotNull(menuList);
   menuList.forEach(System.out::println);
   
   
 }
	@Test
	void 이름_기준_parameter_binding_test() {
	  // 콜론(:) 뒤에 파라미터 이름을 붙여서 사용합니다.
	  String jpql = "SELECT m FROM menu1 m WHERE m.menuCode = :menuCode";
	  
	  Integer menuCode = 8;
	  
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql, Menu.class).setParameter("menuCode", menuCode);
	  
	  Menu menu = typedQuery.getSingleResult();
	  
	  System.out.println(menu);
	}
 
	@Test
	void 위치_기준_parameter_binding_test() {
	  
	  // ? 뒤에 위치 정보를 붙여서 사용합니다. 위치 정보의 시작은 1 입니다.
	  
	  String jpql = "SELECT m FROM menu1 m WHERE m.categoryCode IN(?1, ?2)";
	  
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql, Menu.class)
	                                             .setParameter(1, 5)   // ?1 <--- 5	                                           
	                                             .setParameter(2, 10); // ?2 <--- 10
	  List<Menu> menuList = typedQuery.getResultList();
	  menuList.forEach(System.out::println);
	  
	}
 
	@Test
	void 페이징_API_select_test() {
	  int offset = 0;       // 조회 시작할 위치
	  int display = 10;     // 조회할 행의 개수
	  String sort = "DESC"; // 정렬 방식
	  
	  String jpql = "SELECT m FROM menu1 m ORDER BY m.menuCode " + sort;
	  
	  TypedQuery<Menu> typedQuery = entityManager.createQuery(jpql, Menu.class)
	                                             .setFirstResult(offset)
	                                             .setMaxResults(display);
	  
	  List<Menu> menuList = typedQuery.getResultList();
	  menuList.forEach(System.out::println);
	                                             
	}
 
	@Test
	void 통계함수_count_test() {
	  // COUNT() 함수
	  // 1. 반환 타입이 Long 입니다.
	  // 2. 데이터가 없는 경우 0을 반환합니다.
	  // 3. 반환 타입을 long 과 같은 기본 자료형(Primitive Type)을 사용해도 문제 없습니다.
	  // 모든 통계함수는 결과값의 단위가 싱글이다.
	  
	  
	  String jpql = "SELECT COUNT(m.menuCode) FROM menu1 m WHERE m.categoryCode = ?1";
	  
	  //Integer categoryCode = 10; 데이터 있음
	  Integer categoryCode = 7; // 데이터 없음
	  
	  Long numOfRows = entityManager.createQuery(jpql, Long.class)
	                                .setParameter(1, categoryCode)
	                                .getSingleResult();
	  Assertions.assertThat(numOfRows > 0).isTrue();
	  
	}
	@Test
	void 통계함수_exclude_count_test() {
	  
	  // SUM, AVG, MAX, MIN
	  // 1. 반환 타입이 Long 또는 Double 입니다.
	  // 2. 데이터가 없는 경우 NULL 이 반환됩니다.
	  // 3. 반환 타입을 long 또는 double 와 같은 기본 자료형(Primitive Type)을 사용하면 NullPointerException 이 발생할 우려가 있습니다.
	  // 모든 통계함수는 결과값의 단위가 싱글이다.
	  
	  String jpql = "SELECT SUM(m.menuPrice) FROM menu1 m WHERE m.categoryCode = :categoryCode";
	  
	  //데이터 없음
	  Integer categoryCode = 7;
	  
	  // 2번째 인자 (람다식 함수)의 실행 결과가 NullPointerException 이면 테스트 통과입니다.
	  org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
	    
	    // 쿼리문 실행 결과로 NULL 이 반환되는데 이를 long 타입으로 언박싱(UnBoxing) 할 때 NPE 이 발생합니다.
	    long totalPrice = entityManager.createQuery(jpql, Long.class)
          .setParameter("categoryCode", categoryCode)
          .getSingleResult();
	    
	    Assertions.assertThat(totalPrice > 0).isTrue();
	    System.out.println(totalPrice);
	    
	    
	  });

	}
	
	@Test
	void 그룹화_select_test() {
	  
	  String jpql = "SELECT m.categoryCode, SUM(m.menuPrice) FROM menu1 m GROUP BY m.categoryCode HAVING COUNT(m.menuCode) >= ?1"; 
	  // 동일한 카테고리 코드안에서  2개 이상의 메뉴가 있는 값만 조회하겠다. 
	
	  // HAVING  절에서 사용한 COUNT() 함수의 반환타입은 LONG 이므로
	  // 이와 비교하는 파라미터의 타입토 Long 으로 설정해야 합니다.
	  
	  
	  Long menucount = 2L;
	
	  List<Object[]> categoryPriceList = entityManager.createQuery(jpql, Object[].class)
	                                                  .setParameter(1, menucount)
	                                                  .getResultList();
	  
	  categoryPriceList.forEach(row -> {
	    System.out.println(Arrays.toString(row)); 
	  });
	}
	
	
}
