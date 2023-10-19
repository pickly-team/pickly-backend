package org.pickly.service.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.domain.comment.repository.interfaces.CommentRepository;
import org.pickly.service.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.pickly.service.domain.comment.exception.CommentException.OnlyAuthorCanEditException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService {

  private final CommentRepository commentRepository;
  private final CommentQueryRepository commentQueryRepository;

  public void deleteByBookmark(Long bookmarkId) {
    commentRepository.findAllByBookmarkAndDeletedAtNull(bookmarkId)
        .forEach(Comment::delete);
  }

  public void deleteByCategory(Category category) {
    Member author = category.getMember();
    LocalDateTime now = TimezoneHandler.getNowInTimezone(author.getTimezone());
    commentQueryRepository.deleteByCategory(Collections.singletonList(category.getId()), now);
  }

  public void deleteByCategory(final List<Category> categories) {
    if (!categories.isEmpty()) {
      Member author = categories.get(0).getMember();
      LocalDateTime now = TimezoneHandler.getNowInTimezone(author.getTimezone());
      List<Long> categoryIds = categories.stream().map(BaseEntity::getId).toList();
      commentQueryRepository.deleteByCategory(categoryIds, now);
    }
  }

  public Comment create(Comment comment) {
    return commentRepository.save(comment);
  }

  public void delete(Comment comment) {
    comment.delete();
  }

  public Comment update(Comment comment, Long memberId, String newContent) {
    if (!comment.getMember().getId().equals(memberId)) {
      throw new OnlyAuthorCanEditException();
    }
    comment.updateContent(newContent);
    return comment;
  }

}
