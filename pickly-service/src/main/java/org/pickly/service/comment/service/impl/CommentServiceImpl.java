package org.pickly.service.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.comment.controller.request.CommentCreateReq;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.repository.interfaces.CommentRepository;
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
  private final BookmarkService bookmarkService;
  private final MemberService memberService;

  @Override
  @Transactional
  public void create(final Long bookmarkId, final Long memberId, final CommentCreateReq request) {
    Bookmark bookmark = bookmarkService.findById(bookmarkId);
    Member member = memberService.findById(memberId);
    Comment comment = Comment.create(member, bookmark, request.getContent());
    commentRepository.save(comment);
  }

}
