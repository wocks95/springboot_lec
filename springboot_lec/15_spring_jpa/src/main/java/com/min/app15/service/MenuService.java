package com.min.app15.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.min.app15.model.dto.CategoryDto;
import com.min.app15.model.dto.MenuDto;
import com.min.app15.model.exception.MenuNotFoundException;

public interface MenuService {
  
  List<CategoryDto> findByCategoryList();
  
  MenuDto registMenu(MenuDto menuDto);
  MenuDto modifyMenu(MenuDto menuDto) throws MenuNotFoundException;
  void deleteMenu(Integer menuCode) throws MenuNotFoundException;
  MenuDto findMenuById(Integer menuCode);
  List<MenuDto> findMenuList(Pageable pageable);
  
  List<MenuDto> findByMenuPrice(Integer menuPrice);
  List<MenuDto> findByMenuName(String menuName);
}
