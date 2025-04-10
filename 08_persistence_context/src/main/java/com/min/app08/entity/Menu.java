package com.min.app08.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/*
 * @Entity
 * JPA에서 엔티티 클래스(관계형 데이터베이스의 테이블과 매핑되는 자바 클래스)임을 명시하는 Annotation 입니다.
 * 일반 클래스와는 달리 다른 패키지에 같은 이름을 가진 엔티티가 존재할 수 없습니다.
 * 만약 다른 패키지에 같은 이름의 엔티티가 존재하면 name 속성으로 이들을 구분해야 합니다.
 */

@Entity 
/*
 * @Table
 * JPA 에서 매핑되는 테이블을 정할 때 사용할 수 있습니다.
 */
@Table(name = "tbl_menu")
// 관계형 데이터베이스의 Table 과 연결되는 클래스가 되었음

/*
 * @DynamicUpdate
 * JPA 에서 변경된 엔티티의 필드만 관계형 데이터베이스에 반영하는 Annotation 입니다.
 */
@DynamicUpdate
public class Menu {
  
  /*
   * @Id
   * JPA 에서 해당 필드가 엔티티의 식별자임을 명시하는 Annotation 입니다. 매핑된 테이블의 기본키(PK)와 연결할 수 있습니다.
   */
  @Id
  /*
   * @GeneratedValue
   * JPA 에서 해당 필드는 엔티티의 식별자를 자동으로 생성함을 명시하는 Annotation 입니다.
   * 매핑된 테이블의 기본키가 생성되는 방식에 따라서 strategy 속성을 지정할 수 있습니다.
   * MYSQL의 AUTO_INCREMENT와 같다고 볼 수 있다.
   */
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  /*
   * @Column
   * JPA 에서 해당 필드가 테이블의 칼럼임을 명시하는 Annotation 입니다.
   * 칼럼 이름을 name 속성으로 지정할 수 있습니다.
   */
  @Column(name = "menu_code")
  private int menuCode;
  
  @Column(name = "menu_name")
  private String menuName;
  
  @Column(name = "menu_price")
  private int menuPrice;
  
  @Column(name = "category_code")
  private int categoryCode;
  
  @Column(name = "orderable_status")
  private String orderableStatus;

  
  public Menu() {
    
  }

  public int getMenuCode() {
    return menuCode;
  }

  public void setMenuCode(int menuCode) {
    this.menuCode = menuCode;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public int getMenuPrice() {
    return menuPrice;
  }

  public void setMenuPrice(int menuPrice) {
    this.menuPrice = menuPrice;
  }

  public int getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(int categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getOrderableStatus() {
    return orderableStatus;
  }

  public void setOrderableStatus(String orderableStatus) {
    this.orderableStatus = orderableStatus;
  }

  @Override
  public String toString() {
    return "Menu [menuCode=" + menuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice + ", categoryCode="
        + categoryCode + ", orderableStatus=" + orderableStatus + "]";
  }
  
 
}

