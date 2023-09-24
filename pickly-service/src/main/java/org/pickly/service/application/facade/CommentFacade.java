package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.comment.service.CommentReadService;
import org.pickly.service.domain.comment.service.CommentWriteService;
import org.pickly.service.domain.comment.service.dto.CommentCreateDTO;
import org.pickly.service.domain.comment.service.dto.CommentUpdateDTO;
import org.pickly.service.domain.member.service.interfaces.MemberReadService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentFacade {

  private final CommentReadService commentReadService;
  private final CommentWriteService commentWriteService;
  private final BookmarkReadService bookmarkReadService;
  private final MemberReadService memberReadService;

  public Comment create(Long bookmarkId, Long memberId, CommentCreateDTO request) {
    var bookmark = bookmarkReadService.findByIdWithCategory(bookmarkId);
    var member = memberReadService.findById(memberId);

    var comment = Comment.create(member, bookmark, request.getContent());
    return commentWriteService.create(comment);
  }

  public void delete(Long commentId) {
    var category = commentReadService.findById(commentId);
    commentWriteService.delete(category);
  }

  public Comment update(Long commentId, Long memberId, CommentUpdateDTO request) {
    var comment = commentReadService.findById(commentId);
    return commentWriteService.update(comment, memberId, request.getContent());
  }

}
