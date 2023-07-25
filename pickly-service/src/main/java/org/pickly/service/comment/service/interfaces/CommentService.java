package org.pickly.service.comment.service.interfaces;

import java.util.List;
import org.pickly.service.comment.service.dto.BookmarkCommentDTO;
import org.pickly.service.comment.service.dto.CommentCreateDTO;
import org.pickly.service.comment.service.dto.CommentUpdateDTO;

public interface CommentService {

  BookmarkCommentDTO create(Long bookmarkId, Long memberId, CommentCreateDTO request);

  List<BookmarkCommentDTO> findByBookmark(Long bookmarkId);

  Long countMemberComments(Long memberId);

  List <BookmarkCommentDTO> findByMember(Long memberId);

  void delete(Long commentId);

  BookmarkCommentDTO update(Long commentId, CommentUpdateDTO request);

}
