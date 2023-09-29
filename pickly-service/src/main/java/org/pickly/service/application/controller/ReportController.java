package org.pickly.service.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pickly.service.application.facade.reporter.BookmarkReportFacade;
import org.pickly.service.application.facade.reporter.CommentReportFacade;
import org.pickly.service.application.facade.reporter.MemberReportFacade;
import org.pickly.service.domain.report.dto.controller.request.ReportReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Report", description = "신고 API")
public class ReportController {

  private final BookmarkReportFacade bookmarkReportFacade;
  private final MemberReportFacade memberReportFacade;
  private final CommentReportFacade commentReportFacade;

  @PostMapping("/members")
  @Operation(summary = "특정 유저를 신고한다.")
  public void reportMember(@Valid @RequestBody ReportReq request) {
    memberReportFacade.report(
        request.getReporterId(),
        request.getReportedId(),
        request.getContent()
    );
  }

  @PostMapping("/bookmarks")
  @Operation(summary = "특정 북마크를 신고한다.")
  public void reportBookmark(@Valid @RequestBody ReportReq request) {
    bookmarkReportFacade.report(
        request.getReporterId(),
        request.getReportedId(),
        request.getContent()
    );
  }

  @PostMapping("/comments")
  @Operation(summary = "특정 댓글을 신고한다.")
  public void reportComment(@Valid @RequestBody ReportReq request) {
    commentReportFacade.report(
        request.getReporterId(),
        request.getReportedId(),
        request.getContent()
    );
  }
}
