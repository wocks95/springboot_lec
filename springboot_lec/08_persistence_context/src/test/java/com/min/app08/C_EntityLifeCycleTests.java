package com.min.app08;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
class C_EntityLifeCycleTests {
  
  // 엔티티 매니저 팩토리 
  private static EntityManagerFactory entityManagerf;
  
  // 엔티티 매니저
  private EntityManager entityManager; 
  
  // 전체 테스트를 시작하기 전에 엔티티 매니저 팩토리를 생성합니다. (테스트 클래스가 동작하기 이전)
  @BeforeAll
  static void setEntityManagerFactory() throws Exception {
    entityManagerf = Persistence.createEntityManagerFactory("jpa_test");
  }
  
  // 개별 테스트를 시작하기 전에 엔티티 매니저를 생성합니다. (테스트 메소드가 동작하기 이전)
  @BeforeEach
  void setEntityManager() throws Exception {
    entityManager = entityManagerf.createEntityManager();
  }
  
  // 전체 테스트가 종료되면 엔티티 매니저 팩토리를 소멸합니다. (테스트 클래스가 동작한 이후)
  @AfterAll
  static void closeEntityManagerFactory() throws Exception {
    entityManagerf.close();
  }
  
  // 개별 테스트가 종료될때마다 엔티티 매니저를 소멸합니다. (테스트 메소드가 동작한 이후)
  @AfterEach
  void closeEntityManager() throws Exception {
    entityManager.close();
  }
  
  @Test
  void 비영속_test() {
    
    /*
                  영속                                       비영속
┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
│                                         │   │                                   │
│               foundMenu                 │   │              newMenu              │
│                                         │   │                                   │
└-----------------------------------------┘   └-----------------------------------┘
     */

    // 새로 만든 엔티티는 영속성 컨텍스트에 저장되지 않습니다.
    // 즉, 비영속 엔티티입니다.
    Menu newMenu = new Menu();
    newMenu.setMenuCode(1);
    newMenu.setMenuName("열무김치라떼");
    newMenu.setMenuPrice(4500);
    newMenu.setCategoryCode(8);
    newMenu.setOrderableStatus("Y");
    
    // find() 메소드를 이용해서 조회한 엔티티는 영속성 컨텍스트에 저장됩니다.
    // 즉, 영속 엔티티입니다.
    int menuCode = 1;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // 비영속 엔티티와 영속 엔티티는 서로 다른 객체이기 때문에 둘은 같지 않습니다.
    Assertions.assertThat(newMenu == foundMenu).isTrue();
    
    // 최종 메모리 구조
    //
    //          ┌--------------┐
    //          │ menuCode==1  │0x10000000  :  비영속 엔티티
    //          │ ...          │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          │--------------│
    //          │ menuCode==1  │0x20000000  :  영속 컨텍스트에 저장된 영속 엔티티
    //          │ ...          │
    //          │--------------│
    //          │       .      │
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //          │       .      │
    //          │--------------│
    //   newMenu│  0x10000000  │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          │--------------│
    // foundMenu│  0x20000000  │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          └--------------┘
    //
  }
	
  @Test
  void 영속_test() {
    
    // 조회할 식별자
    int menuCode = 1;
    
    // 엔티티 조회
    // 일단, 영속성 컨텍스트에 menuCode 가 1인 엔티티가 존재하는지 조회합니다.
    // 없으면 데이터베이스에서 조회해 옵니다. (SELECT 문이 실행됩니다.)
    // 조회한 엔티티는 영속성 컨텍스트에 저장합니다.
    /*
                      영속                                관계형 데이터 베이스
    ┌---------- Persistence Context ----------┐   ┌-----------------------------------┐  
    │                                         │   │                                   │
    │                                         │<--│        SELECT FROM WHERE          │
    │                                         │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
     */
    Menu foundMenu1 = entityManager.find(Menu.class, menuCode);
    
    // 엔티티 조회
    // 일단, 영속성 컨텍스트에 menuCode 가 1인 엔티티가 존재하는지 조회합니다.
    // 있으므로 해당 엔티티를 사용합니다.
    /*
                        영속                              
      ┌---------- Persistence Context ----------┐  
      │                                         │   
      │        foundMenu1(menuCode == 1)        │     
      │                                         │   
      └-----------------------------------------┘   
     */
    Menu foundMenu2 = entityManager.find(Menu.class, menuCode);
    
    // 두 엔티티 객체 비교
    System.out.println(foundMenu1.hashCode());
    System.out.println(foundMenu2.hashCode());
    Assertions.assertThat(foundMenu1 == foundMenu2).isTrue();
    
    // 최종 메모리 구조
    //
    //          ┌--------------┐
    //          │ menuCode==1  │0x10000000  :  영속 컨텍스트에 저장된 엔티티
    //          │ ...          │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          │--------------│
    //          │       .      │
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //          │       .      │
    //          │--------------│
    //foundMenu1│  0x10000000  │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          │--------------│
    //foundMenu2│  0x10000000  │
    //          │--------------│
    //          │       .      │
    //          │       .      │
    //          └--------------┘
    //
  }
  
  @Test
  void 영속_추가_test() {
    // 테스트를 위해서 AUTO_INCREMENT 동작을 잠시 중지하고 진행합니다. (Menu 엔티티에서 @GeneratedValue 를 잠시 주석처리합니다.)
    
    // 영속성 컨텍스트에 추가할(Not 관계형 데이터베이스) 엔티티 생성
    /*
                      영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │                                         │   │              newMenu              │
    │                                         │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
     */
    Menu newMenu = new Menu();
    newMenu.setMenuCode(999);
    newMenu.setMenuName("갈치파르페");
    newMenu.setMenuPrice(10000);
    newMenu.setCategoryCode(7);
    
    // 영속성 컨텍스트에 엔티티 추가
    /*
                      영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │         newMenu (menuCode == 999)       │   │                                   │
    │                                         │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
     */    
    entityManager.persist(newMenu);
    
    // 엔티티 조회
    // 일단, 영속성 컨텍스트에서 menuCode 가 999 인 엔티티가 존재하는지 조회합니다.
    // 있으므로 해당 엔티티를 사용합니다.
    /*
                       영속
    ┌---------- Persistence Context ----------┐  
    │                                         │
    │         newMenu (menuCode == 999)       │
    │                                         │
    └-----------------------------------------┘
     */
    int menuCode = 999;
    Menu foundMenu = entityManager.find(Menu.class, menuCode);
    
    // 두 엔티티 비교하기
    Assertions.assertThat(newMenu == foundMenu).isTrue();
  }
  
 @Test
 void 영속_수정_test() {
   
   // 테스트를 위해서 AUTO_INCREMENT 동작을 잠시 중지하고 진행합니다. (Menu 엔티티에서 @GeneratedValue 를 잠시 주석처리합니다.)
   
   // 영속성 컨텍스트에 추가할(Not 관계형 데이터베이스) 엔티티 생성
   /*
                       영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │                                         │   │              newMenu              │
    │                                         │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */
   
   Menu newMenu = new Menu();
   newMenu.setMenuCode(999);
   newMenu.setMenuName("갈치파르페");
   newMenu.setMenuPrice(10000);
   newMenu.setCategoryCode(7);
   
   // 영속성 컨텍스트에 엔티티 추가
   /*
                       영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │      newMenu (menuCode == 999)          │   │                                   │
    │              (menuName == "갈치파르페") │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */   
   
   entityManager.persist(newMenu); // 여길 거치면 영속상태로 바뀜
   // 영속성 컨텍스트의 엔티티를 수정
   /*
                       영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │      newMenu (menuCode == 999)          │   │                                   │
    │              (menuName == "우럭마카롱") │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */
   newMenu.setMenuName("우럭마카롱");
   
   // 엔티티 조회
   // 일단, 영속성 컨텍스트에서 menuCode 가 999 인 엔티티가 존재하는지 조회합니다.
   // 있으므로 해당 엔티티를 사용합니다.
   /*
                       영속                                       비영속
    ┌---------- Persistence Context ----------┐   ┌---------- new/transient ----------┐  
    │                                         │   │                                   │
    │      newMenu (menuCode == 999)          │   │                                   │
    │              (menuName == "우럭마카롱") │   │                                   │
    └-----------------------------------------┘   └-----------------------------------┘
    */
   
   Menu foundMenu = entityManager.find(Menu.class, 999);
   
   // 테스트
   Assertions.assertThat(foundMenu.getMenuName()).isEqualTo("우럭마카롱");
   Assertions.assertThat(foundMenu.getMenuName()).isEqualTo("우럭마카롱");
 }
 @Test
 void 준영속_detach_test() {
   // 준영속
   // 영속성 컨텍스트에서 보관 중이던 엔티티를 분리해서 보관하는 상태를 의미합니다.
   // detach() 메소드를 이용해서 준영속 상태로 만들 수 있습니다.
   
   // 일단, 영속성 컨텍스트에서 menuCode = 20 인 엔티티를 조회합니다.
   // 없으면 관계형 데이터베이스에서 SELECT 를 해 온다.
   // SELECT 결과는 영속성 컨텍스트에 저장합니다.
   Menu foundMenu1 = entityManager.find(Menu.class, 20);
   
   // 일단, 영속성 컨텍스트에서 menuCode = 21 인 엔티티를 조회합니다.
   // 없으면 관계형 데이터베이스에서 SELECT 를 해 온다.
   // SELECT 결과는 영속성 컨텍스트에 저장합니다.
   Menu foundMenu2 = entityManager.find(Menu.class, 21);
   
   // foundMenu1을 준영속 상태로 변경합니다.
   // 이제 foundMenu1은 영속성 컨텍스트에 없습니다.
   entityManager.detach(foundMenu1);
   
   //준영속 상태의 엔티티를 수정합니다.
   foundMenu1.setMenuName("앙버터초무침");
   
   //영속 상태의 엔티티를 수정합니다.
   foundMenu2.setMenuName("홍어회스크류바");
   
   // find() : menuCode = 20 인 엔티티가 영속성 컨텍스트에 없기 때문에 DB 에서 SELECT 해 옵니다.
   // 테스트 실패 
   Assertions.assertThat(entityManager.find(Menu.class, 20).getMenuName()).isEqualTo("앙버터초무침");
   
   // find() : menuCode = 21 인 엔티티가 영속성 컨텍스트에 있기 때문에 해당 엔티티를 가져옵니다.
   // 테스트 성공
   Assertions.assertThat(entityManager.find(Menu.class, 21).getMenuName()).isEqualTo("홍어회스크류바");
 }
 @Test
 void 준영속_clear_test() {
   
   // clear() : 영속성 컨텍스트를 초기화 합니다. 영속 상태인 엔티티들은 모두 준영속 상태가 됩니다.
   
   Menu foundMenu1 = entityManager.find(Menu.class, 20);
   Menu foundMenu2 = entityManager.find(Menu.class, 21);
   
   // foundMenu1, foundMenu2 모두 준영속 상태가 됩니다.
   entityManager.clear();
   
   // 준영속 상태의 엔티티를 수정합니다.
   foundMenu1.setMenuName("앙버터초무침");
   foundMenu2.setMenuName("홍어회스크류바");
   
   // 영속성 컨텍스트에 menuCode 가 20,21인 엔티티가 없으므로 모두 DB 에서 SELECT 해 옵니다.
   // 모든 테스트 실패
   Assertions.assertThat(entityManager.find(Menu.class, 20)).isEqualTo("앙버터초무침");
   Assertions.assertThat(entityManager.find(Menu.class, 21)).isEqualTo("홍어회스크류바");
   
 }
 @Test
 void 준영속_close_test() {
   
   // close() : 영속성 컨텍스트를 종료합니다. EntityManager를 다시 생성해야만 영속성 컨텍스트를 사용할 수 있습니다.
   //           EntityManager 생성 이전에는 IllegalStateException 예외가 발생합니다.
   //           
   
   Menu foundMenu1 = entityManager.find(Menu.class, 20);
   Menu foundMenu2 = entityManager.find(Menu.class, 21);
   
   // 영속성 컨텍스트를 종료합니다. 더 이상 사용할 수 없습니다.
   entityManager.close();
   
   // 준영속 상태의 엔티티를 수정합니다.
   foundMenu1.setMenuName("앙버터초무침");
   foundMenu2.setMenuName("홍어회스크류바");
   
   
   // find() 메소드 동작 시 영속성 컨텍스트에 가장 먼저 접근하는데 현재 영속성 컨텍스트가 닫힌 상태이므로 IllgalStateException 이 발생합니다.
   Assertions.assertThat(entityManager.find(Menu.class, 20)).isEqualTo("앙버터초무침");
   Assertions.assertThat(entityManager.find(Menu.class, 21)).isEqualTo("홍어회스크류바");
 }
 
 @Test
 void 준영속_merge_test() {
   
   //menuCode = 1 인 엔티티는 영속성 컨텍스트에 없으므로 DB 에서 SELECT 해 와서 영속성 컨텍스트에 저장합니다.
   int menuCode = 1;
   Menu foundMenu = entityManager.find(Menu.class, menuCode);
   
   // 영속 -> 준영속으로 변경합니다.
   entityManager.detach(foundMenu);
   
   // 준영속 상태에 있는 foundMenu 엔티티를 영속성 컨텍스트에 반환합니다.
   // mergedMenu 엔티티는 영속 상태이고, foundMenu 엔티티는 준영속 상태입니다.
   Menu mergedMenu = entityManager.merge(foundMenu);
   
   
   // 테스트 실패
   Assertions.assertThat(foundMenu == mergedMenu).isTrue();
   
   // menuCode = 1 인 엔티티를 영속성 컨텍스트에서 조회합니다.
   // 조회 결과 mergedMenu 엔티티가 존재합니다. 따라서 해당 엔티티를 반환합니다.
   Assertions.assertThat(entityManager.find(Menu.class, menuCode) == mergedMenu).isTrue();
 }

 @Test
 void 준영속_merge_update_test() {
   
   // foundMenu 엔티티는 영속 상태입니다.
   // 영속성 컨텍스트의 "1차 캐시"에 foundMenu 엔티티의 정보(@Id, Entity, Snapshot)가 저장됩니다.
   int menuCode = 1;
   Menu foundMenu = entityManager.find(Menu.class, menuCode);
   
   // foundMenu 엔티티는 준영속 상태입니다.
   entityManager.detach(foundMenu);
   
   // 준영속 상태의 foundMenu 엔티티 내용을 수정합니다.
   foundMenu.setMenuName("까나리아메리카노");
   
   // merge() 메소드 동작 순서
   // 1. foundMenu 엔티티의 @Id(menuCode = 1) 값으로 영속성 컨텍스트의 "1차 캐시"에서 엔티티를 조회합니다. "1차 캐시"에 없으면 DB 에서 조회하고 조회 결과 엔티티를 "1차 캐시"에 저장합니다.
   // DB 에서 조회가 안 되면 새로운 영속 엔티티를 생성해서 반환합니다.
   // 2. found Menu 엔티티는 영속성 컨텍스트의 "1차 캐시"에서 조회가 됩니다.
   // 3. "1차 캐시"에서 조회한 엔티티에 준영속 엔티티 foundMenu 의 값을 병합한 영속 엔티티를 반환합니다.
   Menu mergedMenu = entityManager.merge(foundMenu);
   
   
   Assertions.assertThat(entityManager.find(Menu.class, menuCode).getMenuName()).isEqualTo("까나리아메리카노");
   Assertions.assertThat(mergedMenu.getMenuName()).isEqualTo("까나리아메리카노");
   
 }
 @Test
 void 준영속_merge_insert_test() {
   
   int menuCode = 1;
   Menu foundMenu = entityManager.find(Menu.class, menuCode);
   
   entityManager.detach(foundMenu);
   
   foundMenu.setMenuCode(1000);
   foundMenu.setMenuName("시래기라떼");
   
   // foundMenu 의 menuCode = 1000 을 영속성 컨텍스트의 "1차 캐시"에서 찾습니다.
   // "1차 캐시"에 없으므로 DB 에서 찾습니다.
   // DB 에도 없으므로 새로운 엔티티를 생성해서 반환합니다.
   Menu mergedMenu = entityManager.merge(foundMenu);
   
   Assertions.assertThat(mergedMenu.getMenuPrice()).isEqualTo(4500);
 }
 
 @Test
 void 삭제_remove_test() {
   
   // remove() : 영속 상태의 엔티티를 삭제 상태의 엔티티로 변경합니다.
   
   // menuCode = 21 인 엔티티가 영속성 컨텍스트에 저장됩니다.
   // "1차 캐시"에 menuCode = 21 인 엔티티가 저장됩니다.
   int menuCode = 21;
   Menu foundMenu = entityManager.find(Menu.class, menuCode);
   
   // 영속 상태의 foundMenu 엔티티가 삭제 상태가 됩니다.
   // "1차 캐시"에 menuCode = 21 인 엔티티 정보는 남아있습니다.
   entityManager.remove(foundMenu); 
   
   // "1차 캐시"에서 menuCode = 21 인 엔티티 정보를 찾아 반환합니다.
   // 하지만 foundMenu 엔티티와 foundMenu2 엔티티는 서로 다른 상태의 엔티티입니다.
   Menu foundMenu2 = entityManager.find(Menu.class, menuCode);
   
   // 테스트 실패
   Assertions.assertThat(foundMenu == foundMenu2).isTrue();
 }
 
 @Test
 void 삭제_persist_text() {
   
   // persist()
   // 1. 비영속 상태의 엔티티(대표적으로 new)를 영속 상태로 만듭니다.
   // 2. 삭제 상태의 엔티티를 영속 상태로 만듭니다.
   
   // foundMenu 엔티티는 DB 에서 SELECT 해 온 뒤 영속성 컨텍스트에 저장한 영속 상태입니다.
   int menuCode = 21;
   Menu foundMenu = entityManager.find(Menu.class, menuCode);
   
   // foundMenu 엔티티를 삭제 상태로 바꿉니다.
   entityManager.remove(foundMenu);
   
   // foundMenu 엔티티를 영속 상태로 바꿉니다.
   entityManager.persist(foundMenu);
   
   // 영속성 컨텍스트에 menuCode = 21 인 엔티티가 있으므로 해당 엔티티를 반환합니다.
   Menu foundMenu2 = entityManager.find(Menu.class, menuCode);
   
   // 테스트 성공
   Assertions.assertThat(foundMenu == foundMenu2).isTrue();
   
   
 }
 
 
}
