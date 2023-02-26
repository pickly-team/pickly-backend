package org.pickly.service.bookmark.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

  private final BookmarkRepository bookmarkRepository;
  private final MemberService memberService;

  @Override
  public Long countMemberLikes(Long memberId) {
    memberService.existsById(memberId);
    return bookmarkRepository.countAllByMemberId(memberId);
  }

}
