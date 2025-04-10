package com.min.app02;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app02.dto.BoardDto;
import com.min.app02.mapper.IBoardMapper;

@SpringBootTest
class MapperTests {

  @Autowired
  IBoardMapper boardMapper;
  
	@Test
	void now_테스트() {
	  Assertions.assertThat(boardMapper.now().split(" ")[0]).isEqualTo("2025-01-17");
	}
	
	@Test
	void board_list_테스트() {
	  Assertions.assertThat(boardMapper.selectBoardList().size()).isEqualTo(1);
	}

	@Test
	void board_detail_테스트() {
	  Assertions.assertThat(boardMapper.selectBoardById(1).getTitle()).isEqualTo("첫 번째 게시글");
	}
	
	@Test
	void board_insert_테스트() {
	  BoardDto boardDto = BoardDto.builder()
	                          .title("삽입 테스트")
	                          .contents("테스트 진행 중")
	                          .build();
	  Assertions.assertThat(boardMapper.insertBoard(boardDto)).isEqualTo(1);
	  System.out.println(boardDto.getBoardId()); // 삽입된 key 값
	}
	
	@Test
	void board_update_테스트() {
	  Assertions.assertThat(boardMapper.updateBoard("두 번째 게시글", "두 번째 게시글입니다.", 1));
	}
	
	@Test
	void board_delete_테스트() {
	  Assertions.assertThat(boardMapper.deleteBoard(1));
	}
	

	
}
