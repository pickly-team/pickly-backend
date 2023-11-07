package org.pickly.service.domain.bookmark.vo;

import lombok.Getter;

import static org.pickly.service.domain.bookmark.exception.BookmarkException.InvalidReadStatusException;

@Getter
public class BookmarkReadStatus {

  private final long total;
  private final long readCount;
  private final long unreadCount;
  private final long readStatusPercentage;

  public BookmarkReadStatus(final long total, final long readCount) {
    if (total < readCount) {
      throw new InvalidReadStatusException();
    }

    this.total = total;
    this.readCount = readCount;
    this.unreadCount = total - readCount;

    if (total == 0) {
      this.readStatusPercentage = 0;
    } else {
      this.readStatusPercentage = Math.round(((double) readCount / total) * 100);
    }
  }
}
