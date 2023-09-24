package org.pickly.service.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.comment.exception.CommentException;
import org.pickly.service.domain.comment.repository.interfaces.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService {

  private final CommentRepository commentRepository;

  public void deleteByBookmark(Long bookmarkId) {
    commentRepository.findAllByBookmarkAndDeletedAtNull(bookmarkId)
        .forEach(Comment::delete);
  }

  public Comment create(Comment comment) {
    return commentRepository.save(comment);
  }

  public void delete(Comment comment) {
    comment.delete();
  }

  public Comment update(Comment comment, Long memberId, String newContent) {
    if (!comment.getMember().getId().equals(memberId)) {
      throw new CommentException.OnlyAuthorCanEditException();
    }
    comment.updateContent(newContent);
    return comment;
  }

}
