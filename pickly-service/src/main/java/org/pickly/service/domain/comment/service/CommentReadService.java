package org.pickly.service.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.comment.exception.CommentException;
import org.pickly.service.domain.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.domain.comment.repository.interfaces.CommentRepository;
import org.pickly.service.domain.comment.service.dto.CommentDTO;
import org.pickly.service.domain.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReadService {

  private final CommentRepository commentRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final MemberService memberService;

  public List<CommentDTO> findByBookmark(final Long bookmarkId) {
    return commentQueryRepository.findComments(null, bookmarkId);
  }

  public Long countMemberComments(final Long memberId) {
    memberService.existsById(memberId);
    return commentRepository.countAllByMemberIdAndDeletedAtNull(memberId);
  }

  public List<CommentDTO> findByMember(Long memberId) {
    return commentQueryRepository.findComments(memberId, null);
  }


  public Comment findById(final Long id) {
    return commentRepository.findByIdAndDeletedAtNull(id)
        .orElseThrow(CommentException.CommentNotFoundException::new);
  }

}
