package com.min.app09;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app09.entity2.User;

@SpringBootTest
class EntityMappingTests2 {

  // 엔티티 매니저 팩토리 
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
	void 칼럼_테스트() {
	 
	  User user = new User();
	  
	  user.setUserEmail("admin@gmail.com");
	  user.setUserPassword("admin");
	  user.setUserPhone("010-1234-1234");
	  user.setNickname("관리자");
	  user.setAddress("서울시 강남구 역삼동");
	  user.setCreateDt(new Date());
	  user.setUserRole("ADMIN");
	  
	  //DB에 저장
	  EntityTransaction entityTransaction = entityManager.getTransaction();
	  entityTransaction.begin();
	  try {
      entityManager.persist(user);
      entityTransaction.commit();
      
    } catch (Exception e) {
      e.printStackTrace();
      entityTransaction.rollback();
    }
	  
	  String jpql = "SELECT u.userId FROM user2 u"; // SELECT 필드 FROM 엔티티
	  List<Integer>userIds = entityManager.createQuery(jpql, Integer.class).getResultList();
	  System.out.println(userIds);
	  userIds.forEach(userId -> System.out.println(userId));
	  userIds.forEach(System.out::println);
	  
	}

}
