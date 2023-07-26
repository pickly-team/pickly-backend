package org.pickly.service.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.comment.common.CommentMapper;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.exception.CommentException;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.comment.repository.interfaces.CommentRepository;
import org.pickly.service.comment.service.dto.CommentCreateDTO;
import org.pickly.service.comment.service.dto.CommentDTO;
import org.pickly.service.comment.service.dto.CommentUpdateDTO;
import org.pickly.service.comment.service.interfaces.CommentService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
  public CommentDTO create(final Long bookmarkId, final Long memberId,
      final CommentCreateDTO request) {
    Bookmark bookmark = bookmarkService.findByIdWithCategory(bookmarkId);
    Member member = memberService.findById(memberId);
    Comment comment = Comment.create(member, bookmark, request.getContent());
    commentRepository.save(comment);
    return commentMapper.toBookmarkCommentDTO(comment);
  }

  @Override
  public List<CommentDTO> findByBookmark(final Long bookmarkId) {
    return commentQueryRepository.findComments(null, bookmarkId);
  }

  @Override
  public Long countMemberComments(final Long memberId) {
    memberService.existsById(memberId);
    return commentRepository.countAllByMemberIdAndDeletedAtNull(memberId);
  }

  @Override
  public List<CommentDTO> findByMember(Long memberId) {
    return commentQueryRepository.findComments(memberId, null);
  }

  @Override
  @Transactional
  public void delete(final Long commentId) {
    Comment comment = findById(commentId);
    comment.delete();
  }

  @Override
  @Transactional
  public CommentDTO update(final Long commentId, final Long memberId, final CommentUpdateDTO request) {
    Comment comment = findById(commentId);

    Member member = comment.getMember();

    if (!member.getId().equals(memberId)) {
      throw new CommentException.OnlyAuthorCanEditException();
    }

    comment.updateContent(request.getContent());
    return commentMapper.toBookmarkCommentDTO(comment);
  }

  @Override
  public Comment findById(final Long id) {
    return commentRepository.findByIdAndDeletedAtNull(id)
        .orElseThrow(CommentException.CommentNotFoundException::new);
  }

}
