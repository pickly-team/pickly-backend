package org.pickly.service.domain.report.service.comment;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.report.entity.CommentReport;
import org.pickly.service.domain.report.exception.ReportException;
import org.pickly.service.domain.report.repository.CommentReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReportWriteService {

  private final CommentReportRepository commentReportRepository;

  public void save(Member fromMember, Comment toComment, String content) {
    checkIsMyComment(fromMember.getId(), toComment.getMember().getId());
    commentReportRepository.save(CommentReport.create(fromMember, toComment, content));
  }

  private void checkIsMyComment(Long reporterId, Long authorId) {
    if (reporterId.equals(authorId)) {
      throw new ReportException.CannotReportSelfException();
    }
  }

}
