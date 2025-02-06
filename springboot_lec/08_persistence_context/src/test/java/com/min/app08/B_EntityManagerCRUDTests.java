package com.min.app08;

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

import com.min.app08.entity.Menu;

@SpringBootTest
class B_EntityManagerCRUDTests {
  
  // 엔티티 매니저 팩토리 
  private static EntityManagerFactory entityManagerfactory;
  
  // 엔티티 매니저
  private EntityManager entityManager; 
  
  // 전체 테스트를 시작하기 전에 엔티티 매니저 팩토리를 생성합니다. (테스트 클래스가 동작하기 이전)
  @BeforeAll
  static void setEntityManagerFactory() throws Exception {
    entityManagerfactory = Persistence.createEntityManagerFactory("jpa_test");
  }
  
  // 개별 테스트를 시작하기 전에 엔티티 매니저를 생성합니다. (테스트 메소드가 동작하기 이전)
  @BeforeEach
  void setEntityManager() throws Exception {
    entityManager = entityManagerfactory.createEntityManager();
  }
  
  // 전체 테스트가 종료되면 엔티티 매니저 팩토리를 소멸합니다. (테스트 클래스가 동작한 이후)
  @AfterAll
  static void closeEntityManagerFactory() throws Exception {
    entityManagerfactory.close();
  }
  
  // 개별 테스트가 종료될때마다 엔티티 매니저를 소멸합니다. (테스트 메소드가 동작한 이후)
  @AfterEach
  void closeEntityManager() throws Exception {
    entityManager.close();
  }
  
	@Test
	void insert_test() {
	  
	  //엔티티 생성
	  Menu menu = new Menu();
	  menu.setMenuName("아메리카노");
	  menu.setMenuPrice(2000);
	  menu.setOrderableStatus("Y");
	  menu.setCategoryCode(1);
	  
	  // 엔티티 매니저의 트랜잭션 처리를 위한 EntityTransaction 인스턴스 생성
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  
	  // 트랜잭션 시작
	  entityTransaction.begin();
	  
	  try {
	    // 영속성 컨텍스트에 엔티티를 저장
	    entityManager.persist(menu);
	    
	    // 커밋 (엔티티 매니저가 관리하는 모든 엔티티가 관계형데이터베이스에 반영됩니다. 관계형데이터베이스에 쿼리를 전송하여 엔티티를 추가합니다.)
	    entityTransaction.commit();
	          
    } catch (Exception e) {
      //예외 추적
      e.printStackTrace();
      
      //롤백 (엔티티 매니저가 관리하는 모든 엔티티가 이전 상태로 되돌아갑니다. 롤백 이후 다시 begin() 메소드를 호출하여 트랜잭션을 다시 시작할 수 있습니다.)
      entityTransaction.rollback();
    }
	  
	  // 테스트(엔티티 매니저가 menu 엔티티를 가지고 있는지 확인)
	  Assertions.assertThat(entityManager.contains(menu)).isTrue();
	  
	}
	@Test
	void select_test() {
	  
	  // 조회할 식별자 선언
	  int menuCode = 1;
	  
	  // 엔티티 매니저를 이용해 엔티티 조회 (조회 시 식별자를 이용해서 조회합니다. 없으면 null 이 반환합니다.)
	  Menu foundMenu = entityManager.find(Menu.class, menuCode);
	  
	  // 조회한 엔티티 출력
	  System.out.println(foundMenu);
	  
	  // 존재하는지 테스트
	  Assertions.assertThat(foundMenu).isNotNull();
	  // Assertions.assertThat(foundMenu.getMenuName()).isEqualTo("아메리카노");
	  
	}
	@Test
	void update_test() {
	  
	  //수정하고자 하는 엔티티를 조회
	  int menuCode = 1;
	  Menu foundMenu = entityManager.find(Menu.class, menuCode);
	  
	  // EntityTransaction 객체 생성
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  
	  // 트랜잭션 시작
	  entityTransaction.begin();
	  
	  // 수정할 내용 선언(메뉴이름)
	  String menuName = "냉삼라떼";
	  try {
	    
	    // Dirty Checking
	    // 영속성 컨텍스트에 저장된 엔티티에 상태가 변경되면, 커밋 시점에 자동으로 해당 변경 내용이 관계형 데이터베이스에 반영되는 것을 의미합니다.
	    // 정리) 영속 상태인 엔티티가 커밋 시점에 관계형 데이터베이스에 적용됩니다.
	    // 수정과 관련된 메소드가 없다.
	    foundMenu.setMenuName(menuName);
	    
	    // 커밋 (엔티티의 변경 사항이 관계형 데이터베이스에 반영됩니다.)
	    entityTransaction.commit();
	    
    } catch (Exception e) {
      e.printStackTrace();
      entityTransaction.rollback();
    }
	  //수정된 내용 확인
	  Assertions.assertThat(entityManager.find(Menu.class, menuCode).getMenuName()).isEqualTo(menuName);
	  
	  
	}
	@Test
	void delete_test() {
	  
	  // 삭제하고자 하는 엔티티를 조회
	  int menuCode = 1;
	  Menu foundMenu = entityManager.find(Menu.class, menuCode);
	  
	  // EntityTransaction 객체 생성
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  
	  // 트랜잭션 시작
	  entityTransaction.begin();
	  
	  try {
      
	    // 영속성 컨텍스트에 있는 엔티티를 삭제(removed)상태로 변경
	    entityManager.remove(foundMenu);
	    
	    // 커밋 (관계형 데이터베이스에 쿼리문을 전송하여 엔티티를 삭제합니다.)
	    entityTransaction.commit();
	    
    } catch (Exception e) {
      // 예외 추적
      e.printStackTrace();
      
      // 롤백
      entityTransaction.rollback();
    }
	  // 엔티티가 삭제되었는지 확인하는 테스트
	  Assertions.assertThat(entityManager.find(Menu.class, menuCode)).isNull();
	}
	
}
