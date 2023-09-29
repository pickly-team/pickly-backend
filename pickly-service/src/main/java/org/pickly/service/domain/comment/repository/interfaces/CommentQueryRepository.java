package org.pickly.service.domain.comment.repository.interfaces;

import java.util.List;
import java.util.Map;
import org.pickly.service.domain.comment.dto.service.CommentDTO;

public interface CommentQueryRepository {

  Map<Long, Long> findBookmarkCommentCntByMember(Long memberId);

  List<CommentDTO> findComments(Long memberId, Long bookmarkId);
}
