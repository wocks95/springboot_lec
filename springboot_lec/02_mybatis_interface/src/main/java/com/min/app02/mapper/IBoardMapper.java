package com.min.app02.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.min.app02.dto.BoardDto;

@Mapper
public interface IBoardMapper {
  String now();
  List<BoardDto> selectBoardList();
  BoardDto selectBoardById(int boardId);
  int insertBoard(BoardDto boardDto);
  int updateBoard(String title, String contents, int boardId);
  int deleteBoard(int boardId);
  
}
