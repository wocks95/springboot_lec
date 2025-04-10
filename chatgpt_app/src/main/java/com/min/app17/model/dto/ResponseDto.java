package com.min.app17.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * 응답Dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseDto {

  private List<Choice> choices;
  
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @ToString
  public static class Choice {
    private Message message;
  }
  
  
}
