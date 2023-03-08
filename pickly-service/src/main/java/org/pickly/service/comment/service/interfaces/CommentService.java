package org.pickly.service.comment.service.interfaces;

import java.util.List;
import org.pickly.service.comment.controller.request.CommentCreateReq;
import org.pickly.service.comment.service.dto.CommentDTO;

public interface CommentService {

  void create(Long bookmarkId, Long memberId, CommentCreateReq request);

  List<CommentDTO> findByBookmark(Long bookmarkId);

}
