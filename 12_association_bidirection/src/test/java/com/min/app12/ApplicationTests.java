package com.min.app12;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app12.entity.Category;
import com.min.app12.entity.Menu;

@SpringBootTest
class ApplicationTests {

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
	void 양방향_객체_그래프_탐색_select_test() {
	  
	  //조회할 데이터
	  Integer menuCode = 3;
	  Integer categoryCode = 10;
	  
	  //주인 엔티티 조회
	  // 주인 엔티티와 연관 관계에 있는 엔티티가 조인된 결과가 나타납니다.
	  Menu foundMenu = entityManager.find(Menu.class, menuCode);
	  Assertions.assertThat(foundMenu.getMenuName()).isEqualTo("생갈치쉐이크");
	  
	  // 주인이 아닌 엔티티 조회
	  Category foundCategory = entityManager.find(Category.class, categoryCode);
	  Assertions.assertThat(foundCategory.getCategoryName()).isEqualTo("기타");
	  
	  /*
	   * toString() 메소드 오버라이드 시
	   * 주인 엔티티의 toString() 메소드는 주인이 아닌 엔티티의 toString() 메소드를 호출하고,
	   * 주인이 아닌 엔티티의 toString() 메소드는 주인 엔티티의 toString() 메소드를 호출합니다.
	   * 즉, StackOverFlowError 가 발생합니다. (메소드 무한 호출)
	   * 
	   * 해결
	   * 주인이 아닌 엔티티의 toString() 메소드에 주인 엔티티의 toString() 메소드 호출 부분을 제거합니다.
	   * 
	   * 주의
	   * 양방향 연관 관계에서는 롬복 라이브러리의 @ToString 을 사용하지 않도록 주의해야 합니다.
	   */
	  
	  //조회한 주인 엔티티 출력
	  //System.out.println(foundMenu);
	  
	  //조회한 주인이 아닌 엔티티 출력
	  System.out.println(foundCategory);
	  
	  // foundCategory 엔티티에 사실은 포함되어 있는 List<Menu> menuList 출력
	  // Menu 엔티티 출력을 위해서 메뉴 테이블을 조회하는 SELECT 문이 추가로 호출됩니다.
	  // foundCategory.getMenuList().forEach(menu -> System.out.println(menu)); 아래 코드와 동일합니다.
	  foundCategory.getMenuList().forEach(System.out::println); // 호출할 메소드만 작성하는 메소드 참조
	}

	@Test
	void 양방향_주인_엔티티_insert_test() {
	  
	  // 주인 엔티티
	  Menu menu = new Menu();
	  menu.setMenuCode(200);
	  menu.setMenuName("한우마요네즈국밥");
	  menu.setMenuPrice(10000);
	  menu.setCategory(entityManager.find(Category.class, 4)); // 한식 카테고리(category_code)에 포함합니다.
	  menu.setOrderableStatus("Y");
	  
	  // 저장
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  entityTransaction.begin();
	  entityManager.persist(menu);
	  entityTransaction.commit();
	  
	  // 저장된 엔티티 조회
	  Menu foundMenu = entityManager.find(Menu.class, 200);
	  System.out.println(foundMenu);

	}
	@Test
	void 양방향_주인_아닌_엔티티_insert_test() {
	  // 주인 아닌 엔티티 생성
	  Category category = new Category();
	  category.setCategoryCode(13);
	  category.setCategoryName("기타");
	  category.setRefCategoryCode(null);
	  
	  //저장
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  entityTransaction.begin();
	  entityManager.persist(category);
	  entityTransaction.commit();
	  
	  Category foundCategory = entityManager.find(Category.class, 13);
	  System.out.println(foundCategory);
	  
	}
	
	
	
	
}
