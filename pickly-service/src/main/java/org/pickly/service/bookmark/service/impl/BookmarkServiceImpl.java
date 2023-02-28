package org.pickly.service.bookmark.service.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

  private final BookmarkRepository bookmarkRepository;


  @Override
  @Transactional
  public void likeBookmark(Long bookmarkId) {
    Bookmark bookmark = bookmarkRepository.findOneById(bookmarkId)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 북마크입니다."));

    bookmark.updateUserLike();
  }

  @Override
  public void unlikeBookmark(Long bookmarkId) {
    Bookmark bookmark = bookmarkRepository.findOneById(bookmarkId)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 북마크입니다."));

    bookmark.updateUserUnlike();
  }
}
