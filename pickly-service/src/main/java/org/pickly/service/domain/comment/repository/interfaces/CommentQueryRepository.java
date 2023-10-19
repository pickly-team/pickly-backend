package org.pickly.service.domain.comment.repository.interfaces;

import org.pickly.service.domain.comment.dto.service.CommentDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CommentQueryRepository {

  Map<Long, Long> findBookmarkCommentCntByMember(Long memberId);

  List<CommentDTO> findComments(Long memberId, Long bookmarkId);

  List<CommentDTO> findCommentsWithoutBlock(Long memberId, Long bookmarkId);

  void deleteByCategory(List<Long> categories, LocalDateTime now);

}
