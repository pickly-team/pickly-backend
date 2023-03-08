package org.pickly.service.comment.service.interfaces;

import org.pickly.service.comment.controller.request.CommentCreateReq;

public interface CommentService {

  void create(Long bookmarkId, Long memberId, CommentCreateReq request);

}
