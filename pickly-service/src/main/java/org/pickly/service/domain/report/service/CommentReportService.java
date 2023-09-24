package org.pickly.service.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.comment.service.interfaces.CommentReadService;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.service.interfaces.MemberReadService;
import org.pickly.service.domain.report.exception.ReportException;
import org.pickly.service.domain.report.repository.CommentReportRepository;
import org.pickly.service.domain.report.entity.CommentReport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReportService {

  private final CommentReportRepository commentReportRepository;
  private final MemberReadService memberReadService;
  private final CommentReadService commentReadService;

  public void reportComment(Long reporterId, Long reportedId, String content) {
    checkIsAlreadyReport(reporterId, reportedId);
    Member reporter = memberReadService.findById(reporterId);
    Comment comment = commentReadService.findById(reportedId);
    checkIsMyComment(reporterId, comment.getMember().getId());
    commentReportRepository.save(CommentReport.create(reporter, comment, content));
  }

  private void checkIsAlreadyReport(Long reporterId, Long reportedId) {
    if (commentReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new ReportException.AlreadyReportException();
    }
  }

  private void checkIsMyComment(Long reporterId, Long authorId) {
    if (reporterId.equals(authorId)) {
      throw new ReportException.CannotReportSelfException();
    }
  }

}
