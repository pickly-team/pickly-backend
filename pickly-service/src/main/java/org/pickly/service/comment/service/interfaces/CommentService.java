package org.pickly.service.comment.service.interfaces;

import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.service.dto.CommentCreateDTO;
import org.pickly.service.comment.service.dto.CommentDTO;
import org.pickly.service.comment.service.dto.CommentUpdateDTO;

import java.util.List;

public interface CommentService {

  CommentDTO create(Long bookmarkId, Long memberId, CommentCreateDTO request);

  List<CommentDTO> findByBookmark(Long bookmarkId);

  Long countMemberComments(Long memberId);

  List <CommentDTO> findByMember(Long memberId);

  Comment findById(Long commentId);

  void delete(Long commentId);

  CommentDTO update(Long commentId, Long memberId, CommentUpdateDTO request);

}
