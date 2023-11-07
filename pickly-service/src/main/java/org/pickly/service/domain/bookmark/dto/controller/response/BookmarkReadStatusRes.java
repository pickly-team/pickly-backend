package org.pickly.service.domain.bookmark.dto.controller.response;

import lombok.Builder;

@Builder
public record BookmarkReadStatusRes(

    long total,
    long readCount,
    long unreadCount,
    long readStatusPercentage

) {
}
