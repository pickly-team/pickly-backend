package org.pickly.service.application.facade.reporter;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.comment.service.CommentReadService;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.report.service.comment.CommentReportReadService;
import org.pickly.service.domain.report.service.comment.CommentReportWriteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReportFacade {

  private final CommentReportReadService commentReportReadService;
  private final CommentReportWriteService commentReportWriteService;
  private final MemberReadService memberReadService;
  private final CommentReadService commentReadService;

  public void report(Long fromMemberId, Long toCommentId, String content) {
    commentReportReadService.checkIsAlreadyReport(fromMemberId, toCommentId);

    var member = memberReadService.findById(fromMemberId);
    var comment = commentReadService.findById(toCommentId);
    commentReportWriteService.save(member, comment, content);
  }

}
