package org.pickly.service.config;

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

}