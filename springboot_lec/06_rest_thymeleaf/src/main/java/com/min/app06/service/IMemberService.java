package com.min.app06.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface IMemberService {
  Map<String, Object> getMembers(HttpServletRequest request);
  Map<String, Object> getMemberById(int memId);
  Map<String, Object> registMember(Map<String, Object> params);
  Map<String, Object> modifyMember(Map<String, Object> params);
  int removeMember(int memId);
  int removeSelectMember(String memIds);
}
