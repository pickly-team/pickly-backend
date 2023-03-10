package org.pickly.service.comment.repository.interfaces;

import java.util.Map;

public interface CommentQueryRepository {

  Map<Long, Long> findBookmarkCommentCntByMember(Long memberId);

}
