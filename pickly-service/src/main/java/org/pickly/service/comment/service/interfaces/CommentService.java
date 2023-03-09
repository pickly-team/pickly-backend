package org.pickly.service.comment.service.interfaces;

import java.util.List;
import org.pickly.service.comment.service.dto.CommentCreateDTO;
import org.pickly.service.comment.service.dto.CommentDTO;

public interface CommentService {

  CommentDTO create(Long bookmarkId, Long memberId, CommentCreateDTO request);

  List<CommentDTO> findByBookmark(Long bookmarkId);

}
