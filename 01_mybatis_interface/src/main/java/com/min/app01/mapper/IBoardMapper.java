package com.min.app01.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.min.app01.dto.BoardDto;

@Mapper // 나는 마이바티스 매퍼입니다.
public interface IBoardMapper {

  @Select("SELECT NOW()")
  String now();
  
  @Select("SELECT board_id, title, contents, create_dt FROM tbl_board ORDER BY board_id DESC")
  List<BoardDto> selectBoardList();
  
  @Select("SELECT board_id, title, contents, create_dt FROM tbl_board WHERE board_id=#{boardId}")
  BoardDto selectBoardById(@Param("boardId") int boardId);
  
  
  @Insert("INSERT INTO tbl_board VALUE(null, #{title}, #{contents}, NOW())")
  int insertBoard(BoardDto boardDto);
  
  @Update("UPDATE tbl_board SET title = #{title}, contents = #{contents} WHERE board_id=#{boardId}")
  int updateBoard(@Param("title") String title,
                  @Param("contents") String contents,
                  @Param("boardId") int boardId);
  
  @Delete("DELETE FROM tbl_board WHERE board_id = #{boardId}")
  int deleteBoard(@Param("boardId") int boardId);
  
  
}
