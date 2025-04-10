package com.min.app05.model;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Swagger 설정 Annotation
 * @Schema
 */
@Schema(description = "API 성공 응답 메시지")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ResponseMessage {
  @Schema(description = "응답 코드", nullable = false, allowableValues = {"200", "201", "204"})
  private int status;           
  
  @Schema(description = "응답 메시지", nullable = false)
  private String message;       
  
  @Schema(description = "응답 결과", nullable = false)
  private Map<String, Object> results;
  
  
  
}
