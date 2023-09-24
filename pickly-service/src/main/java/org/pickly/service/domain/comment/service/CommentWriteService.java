package org.pickly.service.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.comment.entity.Comment;
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

}
