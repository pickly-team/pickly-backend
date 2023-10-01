package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.comment.service.CommentReadService;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.report.common.ReportServiceFactory;
import org.pickly.service.domain.report.common.ReportType;
import org.pickly.service.domain.report.dto.controller.request.ReportReq;
import org.pickly.service.domain.report.service.ReportService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportFacade {

  private final ReportServiceFactory reportServiceFactory;
  private final MemberReadService memberReadService;
  private final BookmarkReadService bookmarkReadService;
  private final CommentReadService commentReadService;

  public void report(ReportReq req) {
    Member reporter = memberReadService.findById(req.getReporterId());
    Object target = getTarget(req.getReportType(), req.getReportedId());
    ReportService reportService = reportServiceFactory.getReportService(req.getReportType());
    reportService.create(reporter, target, req.getContent());
  }

  public Object getTarget(ReportType reportType, Long reportedId) {
    return switch (reportType) {
      case MEMBER -> memberReadService.findById(reportedId);
      case BOOKMARK -> bookmarkReadService.findById(reportedId);
      case COMMENT -> commentReadService.findById(reportedId);
    };
  }

}
