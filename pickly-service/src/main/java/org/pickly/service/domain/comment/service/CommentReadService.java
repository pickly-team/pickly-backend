package org.pickly.service.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.comment.dto.service.CommentDTO;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.domain.comment.repository.interfaces.CommentRepository;
import org.pickly.service.domain.member.service.MemberReadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.pickly.service.domain.comment.exception.CommentException.CommentNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService {

  private final CommentRepository commentRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final MemberReadService memberReadService;

  public List<CommentDTO> findByBookmark(final Long bookmarkId) {
    return commentQueryRepository.findComments(null, bookmarkId);
  }

  public Long countMemberComments(final Long memberId) {
    memberReadService.existsById(memberId);
    return commentRepository.countAllByMemberIdAndDeletedAtNull(memberId);
  }

  public List<CommentDTO> findByMember(Long memberId) {
    return commentQueryRepository.findComments(memberId, null);
  }


  public Comment findById(final Long id) {
    return commentRepository.findByIdAndDeletedAtNull(id)
        .orElseThrow(CommentNotFoundException::new);
  }

  public Map<Long, Long> getBookmarkCommentCnt(final Long memberId) {
    return commentQueryRepository.findBookmarkCommentCntByMember(memberId);
  }

}
