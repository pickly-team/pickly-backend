package org.pickly.service.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pickly.service.report.controller.request.ReportReq;
import org.pickly.service.report.service.BookmarkReportService;
import org.pickly.service.report.service.MemberReportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Report", description = "신고 API")
public class ReportController {

  private final MemberReportService memberReportService;
  private final BookmarkReportService bookmarkReportService;

  @Operation(summary = "특정 유저 신고")
  @PostMapping("/members")
  public void reportMember(@Valid @RequestBody ReportReq request) {
    memberReportService.reportMember(
        request.getReporterId(),
        request.getReportedId(),
        request.getContent()
    );
  }

  @Operation(summary = "특정 북마크 신고")
  @PostMapping("/bookmarks")
  public void reportBookmark(@Valid @RequestBody ReportReq request) {
    bookmarkReportService.reportBookmark(
        request.getReporterId(),
        request.getReportedId(),
        request.getContent()
    );
  }
}
