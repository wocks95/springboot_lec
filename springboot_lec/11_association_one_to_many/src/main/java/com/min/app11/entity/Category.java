package com.min.app11.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "category")
@Table(name = "tbl_category")
public class Category {

  @Id
  @Column(name = "category_code")
  private Integer categoryCode;
  
  @Column(name = "category_name")
  private String categoryName;
  
  @Column(name = "ref_category_code")
  private Integer refCategoryCode;
  
  /*
   * @JoinColumn : OneToMany(일대다) 관계에서 사용하는 Annotation 입니다.
   */
  @JoinColumn(name = "category_code")
  /*
   * @OneToMany
   * 하나의 Category 에 여러 Menu 가 연결되어 있으므로,
   * Category 구성 시 여러 Menu 를 저장할 List<Menu> 를 추가하고 @OneToMany Annotation 을 추가합니다.
   */
  @OneToMany( cascade = CascadeType.PERSIST,  // 연관 관계에 있는 Menu 엔티티를 함께 등록합니다.
     // fetch = FetchType.EAGER //열심히 조회한다는 의미. 연관 관계를 조회할 때 연관된 테이블을 모두 조회한다. (카테고리 조회 시 메뉴도 함께 조회한다.)
        fetch = FetchType.LAZY // 게으르게 조회한다는 의미(지연 로딩). 연관 관계를 조회할 때 현재 테이블만 조회하고, 나중에 연관 관계의 테이블이 필요할 때 그 때 조회한다.
  )
  private List<Menu> menuList;
  
  public Category() {
    // TODO Auto-generated constructor stub
  }

  public Integer getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(Integer categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Integer getRefCategoryCode() {
    return refCategoryCode;
  }

  public void setRefCategoryCode(Integer refCategoryCode) {
    this.refCategoryCode = refCategoryCode;
  }

  public List<Menu> getMenuList() {
    return menuList;
  }

  public void setMenuList(List<Menu> menuList) {
    this.menuList = menuList;
  }

  @Override
  public String toString() {
    return "Category [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", refCategoryCode="
        + refCategoryCode + ", menuList=" + menuList + "]";
  }
  
}

