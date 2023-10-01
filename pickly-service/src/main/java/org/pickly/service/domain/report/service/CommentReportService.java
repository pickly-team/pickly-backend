package org.pickly.service.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.report.common.ReportType;
import org.pickly.service.domain.report.entity.CommentReport;
import org.pickly.service.domain.report.exception.ReportException;
import org.pickly.service.domain.report.repository.CommentReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReportService extends ReportService<CommentReport> {

  private final CommentReportRepository commentReportRepository;

  @Override
  public CommentReport create(Member reporter, Object reported, String content) {
    Comment target = getTarget(reported);
    checkValidReport(reporter.getId(), target.getMember().getId());
    return commentReportRepository.save(
        CommentReport.builder()
            .reporter(reporter)
            .reported(target)
            .content(content)
            .build()
    );
  }

  private Comment getTarget(Object reported) {
    if (!(reported instanceof Comment)) {
      throw new ReportException.FailToReportException();
    }
    return (Comment) reported;
  }

  @Override
  public ReportType getReportType() {
    return ReportType.COMMENT;
  }
}
