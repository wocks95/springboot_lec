package com.min.app13.entity3;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


// 양방향 관계에서 외래키를 가진 Menu 엔티티가 주인입니다.
@Entity(name = "menu3")
@Table(name = "tbl_menu")
public class Menu {
  
  @Id
  @Column(name = "menu_code")
  private Integer menuCode;
  
  @Column(name = "menu_name")
  private String menuName;
  
  @Column(name = "menu_price")
  private Integer menuPrice;
  
  // Menu - Category 는 다대일 관계
  @JoinColumn(name = "category_code")
  @ManyToOne(cascade = CascadeType.PERSIST
      ,  fetch = FetchType.LAZY)
  private Category category;
  
  @Column(name = "orderable_status")
  private String orderableStatus;
  
  public Menu() {
    // TODO Auto-generated constructor stub
  }

  public Menu(Integer menuCode, String menuName, Integer menuPrice, Category category, String orderableStatus) {
    super();
    this.menuCode = menuCode;
    this.menuName = menuName;
    this.menuPrice = menuPrice;
    this.category = category;
    this.orderableStatus = orderableStatus;
  }

  public Integer getMenuCode() {
    return menuCode;
  }

  public void setMenuCode(Integer menuCode) {
    this.menuCode = menuCode;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public Integer getMenuPrice() {
    return menuPrice;
  }

  public void setMenuPrice(Integer menuPrice) {
    this.menuPrice = menuPrice;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getOrderableStatus() {
    return orderableStatus;
  }

  public void setOrderableStatus(String orderableStatus) {
    this.orderableStatus = orderableStatus;
  }

  @Override
  public String toString() {
    return "Menu [menuCode=" + menuCode + ", menuName=" + menuName + ", menuPrice=" + menuPrice + ", category="
        + category + ", orderableStatus=" + orderableStatus + "]";
  }



}
