package org.pickly.service.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pickly.service.application.facade.ReportFacade;
import org.pickly.service.domain.report.dto.controller.request.ReportReq;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Report", description = "신고 API")
public class ReportController {

  private final ReportFacade reportFacade;

  @PostMapping
  @Operation(summary = "대상을 신고한다.")
  public void report(
      @Valid @RequestBody ReportReq request
  ) {
    reportFacade.report(request);
  }

}
