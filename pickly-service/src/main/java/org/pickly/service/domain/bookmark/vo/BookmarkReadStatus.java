package org.pickly.service.domain.bookmark.vo;

import lombok.Getter;

import static org.pickly.service.domain.bookmark.exception.BookmarkException.InvalidReadStatusException;

@Getter
public class BookmarkReadStatus {

  private final long total;
  private final long readCount;
  private final long unreadCount;
  private final long readStatusPercentage;

  public BookmarkReadStatus(final Long total, final Long readCount) {
    this.total = (total == null) ? 0 : total;
    this.readCount = (readCount == null) ? 0 : readCount;
    if (this.total < this.readCount) {
      throw new InvalidReadStatusException();
    }

    this.unreadCount = this.total - this.readCount;
    if (this.total == 0) {
      this.readStatusPercentage = 100;
    } else {
      this.readStatusPercentage = Math.round(((double) this.readCount / this.total) * 100);
    }
  }
}
