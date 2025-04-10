package com.min.app06.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.min.app06.model.dto.AddrDto;
import com.min.app06.model.dto.MemberDto;

@Mapper
public interface IMemberMapper {
  int selectMemberCount();
  List<MemberDto> selectMemberList(Map<String, Object> params);
  MemberDto selectMemberById(int memId);
  // List<AddrDto> selectAddressListById(int memId);
  AddrDto selectAddressById(int memId);
  int insertMember(MemberDto memberDto);
  int insertAddress(AddrDto addrDto);
  int updateMember(MemberDto addrDto);
  int updateAddress(AddrDto addrDto);
  int deleteMember(int memId);
  int deleteSelectmember(String memIds);
}
