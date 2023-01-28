package org.pickly.service.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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

	@Bean
	@Profile("!prod")
	public GroupedOpenApi customTestOpenAPi() {
		return GroupedOpenApi
			.builder()
			.group("Pickly V1 API")
			.pathsToMatch("/api/v1/**")
			.build();
	}

}