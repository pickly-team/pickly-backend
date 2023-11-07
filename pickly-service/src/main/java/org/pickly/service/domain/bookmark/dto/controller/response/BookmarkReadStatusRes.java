package org.pickly.service.domain.bookmark.dto.controller.response;

import lombok.Builder;

public record BookmarkReadStatusRes(

    long total,
    long readCount,
    long unreadCount,
    long readStatusPercentage

) {

  @Builder
  public BookmarkReadStatusRes(
      long total, long readCount, long unreadCount, long readStatusPercentage
  ) {
    this.total = total;
    this.readCount = readCount;
    this.unreadCount = unreadCount;
    this.readStatusPercentage = readStatusPercentage;
  }
}
