package org.pickly.service.comment.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.comment.common.CommentMapper;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.comment.repository.interfaces.CommentRepository;
import org.pickly.service.comment.service.dto.CommentCreateDTO;
import org.pickly.service.comment.service.dto.CommentDTO;
import org.pickly.service.comment.service.interfaces.CommentService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final CommentMapper commentMapper;
  private final BookmarkService bookmarkService;
  private final MemberService memberService;

  @Override
  @Transactional
  public CommentDTO create(final Long bookmarkId, final Long memberId, final CommentCreateDTO request) {
    Bookmark bookmark = bookmarkService.findWithCategoryById(bookmarkId);
    Member member = memberService.findById(memberId);
    Comment comment = Comment.create(member, bookmark, request.getContent());
    commentRepository.save(comment);
    return commentMapper.toDTO(comment);
  }

  @Override
  public List<CommentDTO> findByBookmark(final Long bookmarkId) {
    return commentQueryRepository.findComments(null, bookmarkId);
  }

  @Override
  public Long countMemberComments(final Long memberId) {
    memberService.existsById(memberId);
    return commentRepository.countAllByMemberId(memberId);
  }

  @Override
  public List<CommentDTO> findByMember(Long memberId) {
    return commentQueryRepository.findComments(memberId, null);
  }

}
