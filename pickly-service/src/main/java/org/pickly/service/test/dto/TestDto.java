package org.pickly.service.test.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestDto {

  @NotBlank(message = "값을 넣어주세요~")
  @Length(min = 1, max = 50, message = "50자 제한이에요~")
  @Schema(description = "이름", example = "스웨거", required = true)
  private String name;

  @NotBlank(message = "패턴 예시~")
  @Pattern(regexp = "[A-Z\\d]{3}", message = "대문자 숫자 조합 3글자~")
  @Schema(description = "패턴", example = "AB1", required = true)
  private String pattern;

}
