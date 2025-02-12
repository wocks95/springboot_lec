package com.min.app15.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.min.app15.model.dto.MenuDto;

public interface MenuService {
  MenuDto registMenu(MenuDto menuDto);
  MenuDto modifyMenu(MenuDto menuDto);
  void deleteMenu(Integer menuCode);
  MenuDto findMenuById(Integer menuCode);
  List<MenuDto> findMenuList(Pageable pageable);
  
  List<MenuDto> findByMenuPrice(Integer menuPrice);
  List<MenuDto> findByMenuName(String menuName);
}
