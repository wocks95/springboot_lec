package com.min.app05.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

  /*
   * Swagger UI
   * 1. OpenAPI Specification 의 이전 이름입니다.
   * 2. 초기 Swagger 는 API 를 설계하고 빌드하고 문서화하는데 사용되었습니다.
   * 3. 현재 Swagger 는 API 문서화, 테스트 기능 등을 제공하면서 API 개발자들의 생산성 향상에 도움을 줍니다.
   * 4. SpringDoc OpenAPI Starter WebMVC UI 디펜던시가 필요합니다.
   *   (스프링부트 3.X 이후로 SpringFox 는 지원하지 않습니다.) 
   */

  @Bean
  OpenAPI openAPI() {
    
    /*
     * Info 
     *   Swagger UI 상단에 표시되는 정보
     * 
     * title       : 문서 제목
     * description : 문서에 대한 설명
     * version     : 문서 버전
     * contact     : 문서 작성자에 대한 정보
     * license     : 문서 라이센스에 대한 정보
     */
    
    /*
     * Swagger UI End Point
     *   http://localhost:8080/swagger-ui/index.html
     */
    
    final Info info = new Info()
                          .title("Spring Boot REST API")
                          .description("REST API 를 지원하는 회원 기능")
                          .version("1.0.0");
    
    return new OpenAPI()
                .components(new Components())
                .info(info);
    
  }
  
}
