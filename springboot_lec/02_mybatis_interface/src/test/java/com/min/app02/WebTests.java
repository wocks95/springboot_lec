package com.min.app02;


import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;


@SpringBootTest
@AutoConfigureMockMvc // MockMvc 객체를 자동으로 생성합니다.
public class WebTests {

  @Autowired
  MockMvc mockMvc;  // web test 할 때 사용하는 객체입니다.
  
  @Test
  void mockMvc_객체_생성_테스트() {
    assertThat(mockMvc).isNotNull();
  }
 
  @Test
  void 목록_보기_테스트() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/list.do"))   // /list.do 요청을 실시합니다.
                                 .andDo(MockMvcResultHandlers.print())             // 요청과 응답 결과를 화면에 출력합니다.
                                 .andExpect(MockMvcResultMatchers.status().isOk()) // 응답 코드가 200(OK)이면 통과입니다.
                                 .andReturn();       
    
    ModelAndView mav = mvcResult.getModelAndView();
    System.out.println(mav.getViewName());
    System.out.println(mav.getModelMap().get("boardList"));
    System.out.println(mav.getModelMap().get("paging"));
  }
  
  @Test
  void 상세_보기_테스트() throws Exception {
    
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/detail.do").param("boardId", "1"))   // /detail.do 요청을 실시합니다.
                                 .andDo(MockMvcResultHandlers.print())             // 요청과 응답 결과를 화면에 출력합니다.
                                 .andExpect(MockMvcResultMatchers.status().isOk()) // 응답 코드가 200(OK)이면 통과입니다.
                                 .andReturn();       
    
    ModelAndView mav = mvcResult.getModelAndView();
    System.out.println(mav.getViewName());
    System.out.println(mav.getModelMap().get("board"));
  }
  
  @Test
  void 신규_등록_테스트() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/regist.do")
                                                                .param("title", "신규 제목")
                                                                .param("contents", "신규 내용"))
                                 .andDo(MockMvcResultHandlers.print())
                                 .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // 302 Found : Redirect 응답 상태 코드(이 상태를 수신하는 브라우저는 새 페이지로 Redirect 합니다.)
                                 .andReturn();
    
    System.out.println(mvcResult.getModelAndView().getViewName()); // redirect 할 경로
    System.out.println(mvcResult.getFlashMap().get("msg"));        // 실행 결과 메시지
  }
 
  @Test
  void 수정_테스트() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/modify.do")
                                                                .param("title", "수정 제목")
                                                                .param("contents", "신규 내용")
                                                                .param("boardId", "1"))
                                 .andDo(MockMvcResultHandlers.print())
                                 .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                                 .andReturn();
    System.out.println(mvcResult.getModelAndView().getViewName());
    System.out.println(mvcResult.getFlashMap().get("msg"));     
  }
  
  @Test
  void 삭제_테스트() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/remove.do")
                                                                .param("boardId", "1"))
                                 .andDo(MockMvcResultHandlers.print())
                                 .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                                 .andReturn();
    System.out.println(mvcResult.getModelAndView().getViewName());
    System.out.println(mvcResult.getFlashMap().get("msg"));     
  }
  
  
  
  
}
