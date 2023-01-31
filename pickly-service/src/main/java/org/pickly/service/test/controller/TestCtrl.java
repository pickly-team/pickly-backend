package org.pickly.service.test.controller;

import org.pickly.service.test.dto.TestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestCtrl {

	@Operation(summary = "API 내용 summary")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공"),
		@ApiResponse(responseCode = "404", description = "실패")
	})
	@GetMapping("/test/{testId}")
	public ResponseEntity<Void> test(
		@Parameter(name = "testId", description = "테스트에용", example = "1", required = true)
		@Positive(message = "테스트 ID는 0보다 커야 합니다.") @PathVariable final long testId,
		@Parameter @RequestBody @Valid TestDto testDto
	) {
		return ResponseEntity.ok().build();
	}

}
