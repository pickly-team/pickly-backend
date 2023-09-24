package org.pickly.service.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.domain.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.pickly.service.domain.category.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkWriteService {

  private final BookmarkReadService bookmarkReadService;
  private final BookmarkRepository bookmarkRepository;

  public void like(Long bookmarkId) {
    var bookmark = bookmarkReadService.findById(bookmarkId);
    bookmark.like();
  }

  public void cancelLike(Long bookmarkId) {
    var bookmark = bookmarkReadService.findById(bookmarkId);
    bookmark.deleteLike();
  }

  public Bookmark create(Bookmark bookmark) {
    return bookmarkRepository.save(bookmark);
  }

  public void delete(Bookmark bookmark) {
    bookmark.delete();
  }

  public void delete(List<Long> bookmarkIds, LocalDateTime now) {
    bookmarkRepository.deleteBookmarksByIds(bookmarkIds, now);
  }

  public void update(Bookmark bookmark, Category category, BookmarkUpdateReqDTO request) {
    bookmark.updateBookmark(
        category,
        request.getTitle(),
        request.getReadByUser(),
        request.getVisibility()
    );
  }

}
