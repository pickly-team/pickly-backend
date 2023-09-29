package org.pickly.service.domain.report.dto.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportReq {

  @Positive
  @Schema(description = "신고자 ID", example = "1")
  private Long reporterId;

  @Positive
  @Schema(description = "신고 대상 ID", example = "2")
  private Long reportedId;

  @NotBlank(message = "신고 내용을 입력해주세요")
  private String content;
}
