package com.min.app08;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class A_EntityManagerLifeCycleTests {
  
  // 엔티티 매니저 팩토리 
  private static EntityManagerFactory emf;
  
  // 엔티티 매니저
  private EntityManager em; 
  
  // 전체 테스트를 시작하기 전에 엔티티 매니저 팩토리를 생성합니다. (테스트 클래스가 동작하기 이전)
  @BeforeAll
  static void setEntityManagerFactory() throws Exception {
    emf = Persistence.createEntityManagerFactory("jpa_test");
  }
  
  // 개별 테스트를 시작하기 전에 엔티티 매니저를 생성합니다. (테스트 메소드가 동작하기 이전)
  @BeforeEach
  void setEntityManager() throws Exception {
    em = emf.createEntityManager();
  }
  
  // 전체 테스트가 종료되면 엔티티 매니저 팩토리를 소멸합니다. (테스트 클래스가 동작한 이후)
  @AfterAll
  static void closeEntityManagerFactory() throws Exception {
    emf.close();
  }
  
  // 개별 테스트가 종료될때마다 엔티티 매니저를 소멸합니다. (테스트 메소드가 동작한 이후)
  @AfterEach
  void closeEntityManager() throws Exception {
    em.close();
  }
  
	@Test
	void life_cycle_test_1() {
	  System.out.println("EntityManagerFactory: " + emf.toString()); // hashcode 값 = 참조값
	  System.out.println("EntityManagerFactory:" + em.toString());
	}

	@Test
  void life_cycle_test_2() {
    System.out.println("EntityManagerFactory: " + emf.toString()); 
    System.out.println("EntityManagerFactory:" + em.toString());
  }

	@Test
  void life_cycle_test_3() {
    System.out.println("EntityManagerFactory: " + emf.toString()); 
    System.out.println("EntityManagerFactory:" + em.toString());
  }

}
