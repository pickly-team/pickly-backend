package org.pickly.service.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.report.controller.request.ReportReq;
import org.pickly.service.domain.report.service.BookmarkReportService;
import org.pickly.service.domain.report.service.CommentReportService;
import org.pickly.service.domain.report.service.MemberReportService;
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
  private final CommentReportService commentReportService;

  @PostMapping("/members")
  @Operation(summary = "특정 유저를 신고한다.")
  public void reportMember(@Valid @RequestBody ReportReq request) {
    memberReportService.reportMember(
        request.getReporterId(),
        request.getReportedId(),
        request.getContent()
    );
  }

  @PostMapping("/bookmarks")
  @Operation(summary = "특정 북마크를 신고한다.")
  public void reportBookmark(@Valid @RequestBody ReportReq request) {
    bookmarkReportService.reportBookmark(
        request.getReporterId(),
        request.getReportedId(),
        request.getContent()
    );
  }

  @PostMapping("/comments")
  @Operation(summary = "특정 댓글을 신고한다.")
  public void reportComment(@Valid @RequestBody ReportReq request) {
    commentReportService.reportComment(
        request.getReporterId(),
        request.getReportedId(),
        request.getContent()
    );
  }
}
