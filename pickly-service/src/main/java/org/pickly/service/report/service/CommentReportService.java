package org.pickly.service.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.service.interfaces.CommentService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.report.entity.CommentReport;
import org.pickly.service.report.exception.ReportException;
import org.pickly.service.report.repository.CommentReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReportService {

  private final CommentReportRepository commentReportRepository;
  private final MemberService memberService;
  private final CommentService commentService;

  public void reportComment(Long reporterId, Long reportedId, String content) {
    checkIsAlreadyReport(reporterId, reportedId);
    Member reporter = memberService.findById(reporterId);
    Comment comment = commentService.findById(reportedId);
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
