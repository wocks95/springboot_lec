package com.min.app05.service;

import java.util.List;

import com.min.app05.model.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public interface IUserService {
  UserDto registUser(UserDto userDto) throws Exception; // 등록할 신규 유저
  UserDto modifyUser(UserDto userDto) throws Exception;
  int removeUser(int userId) throws Exception;
  List<UserDto> getUsers(HttpServletRequest request) throws Exception;
  UserDto getUserById(int userId) throws Exception;
  
}
