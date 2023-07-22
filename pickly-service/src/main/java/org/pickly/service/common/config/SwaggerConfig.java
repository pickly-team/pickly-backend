package org.pickly.service.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
    info = @Info(
        title = "Pickly 서비스 API 명세서",
        description = "즐겨찾기 관리 서비스 Pickly API 명세서",
        version = "v1"
    )
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

  // 추후 인증 & 권한 전역 관리 설정 추가 예정
  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .components(
            new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
        .addServersItem(new Server().url("/"));
  }

  private SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer");
  }
}
