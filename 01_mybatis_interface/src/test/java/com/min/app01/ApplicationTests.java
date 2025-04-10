package com.min.app01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app01.dto.BoardDto;
import com.min.app01.mapper.IBoardMapper;

@SpringBootTest
class ApplicationTests {

  @Autowired
  private IBoardMapper boardMapper;
  
  
	@Test
	void 매퍼_객체_생성_테스트() {
	  Assertions.assertNotNull(boardMapper);
	}
	
	@Test
	void now_테스트() {
	  System.out.println("NOW : " + boardMapper.now());
	}
	
	@Test
	void board_list_테스트() {
	  Assertions.assertEquals(1, boardMapper.selectBoardList().size());
	}

	@Test
	void board_detail_테스트() {
	  Assertions.assertEquals("첫 번째 게시글", boardMapper.selectBoardById(1).getTitle());
	}
	
	@Test
	void board_insert_테스트() {
	  BoardDto boardDto = BoardDto.builder()
	                         .title("테스트 제목")
	                         .contents("테스트 내용")
	                         .build();
	  Assertions.assertEquals(1, boardMapper.insertBoard(boardDto));
	  
	}
	
	@Test
	void board_update_테스트() {
	  Assertions.assertEquals(1, boardMapper.updateBoard("수정 블로그", "인간의욕심은끝이없다.", 1));
	}
	
	@Test
	void board_delete_테스트() {
	  Assertions.assertEquals(1, boardMapper.deleteBoard(1));
	}
}
