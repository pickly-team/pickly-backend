package org.pickly.service.comment.repository.interfaces;

import java.util.List;
import java.util.Map;
import org.pickly.service.comment.service.dto.BookmarkCommentDTO;

public interface CommentQueryRepository {

  Map<Long, Long> findBookmarkCommentCntByMember(Long memberId);

  List<BookmarkCommentDTO> findComments(Long memberId, Long bookmarkId);
}
