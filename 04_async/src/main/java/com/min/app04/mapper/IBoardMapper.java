package com.min.app04.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.min.app04.dto.BoardDto;


@Mapper
public interface IBoardMapper {
  int selectBoardCount();
  List<BoardDto> selectBoardList(Map<String, Object> param);
  BoardDto selectBoardById(int boardId);
  int insertBoard(BoardDto boardDto);
  int updateBoard(BoardDto boardDto);
  int deleteBoard(int boardId);
  
}
