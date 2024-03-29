package org.pickly.service.domain.bookmark.common;

import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.bookmark.dto.controller.request.BookmarkUpdateReq;
import org.pickly.service.domain.bookmark.dto.controller.response.BookmarkCrawlInfoRes;
import org.pickly.service.domain.bookmark.dto.controller.response.BookmarkReadStatusRes;
import org.pickly.service.domain.bookmark.dto.controller.response.BookmarkRes;
import org.pickly.service.domain.bookmark.dto.controller.response.CategoryReadStatusRes;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.pickly.service.domain.bookmark.vo.BookmarkCrawlInfo;
import org.pickly.service.domain.bookmark.vo.BookmarkReadStatus;
import org.pickly.service.domain.category.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BookmarkMapper {

  public BookmarkRes entityToResponseDto(final Bookmark bookmark) {
    Category category = bookmark.getCategory();
    String timezone = bookmark.getMember().getTimezone();
    return BookmarkRes
        .builder()
        .id(bookmark.getId())
        .categoryId(category.getId())
        .categoryName(category.getName())
        .categoryEmoji(category.getEmoji())
        .memberId(bookmark.getMember().getId())
        .url(bookmark.getUrl())
        .title(bookmark.getTitle())
        .previewImageUrl(bookmark.getPreviewImageUrl())
        .isUserLike(bookmark.getIsUserLike())
        .readByUser(bookmark.getReadByUser())
        .visibility(bookmark.getVisibility().getDescription())
        .createdAt(TimezoneHandler.convertToTimezone(bookmark.getCreatedAt(), timezone))
        .build();
  }

  public BookmarkUpdateReqDTO toUpdateDto(final BookmarkUpdateReq request) {
    return BookmarkUpdateReqDTO
        .builder()
        .categoryId(request.getCategoryId())
        .title(request.getTitle())
        .readByUser(request.getReadByUser())
        .visibility(request.getVisibility())
        .build();
  }

  public BookmarkReadStatusRes toReadStatusDto(final BookmarkReadStatus status) {
    return BookmarkReadStatusRes.builder()
        .total(status.getTotal())
        .readCount(status.getReadCount())
        .unreadCount(status.getUnreadCount())
        .readStatusPercentage(status.getReadStatusPercentage())
        .build();
  }

  public CategoryReadStatusRes toCategoryReadStatusDto(final Category category, final BookmarkReadStatus status) {
    return new CategoryReadStatusRes(category, status);
  }

  public List<CategoryReadStatusRes> toCategoryReadStatusDto(final Map<Category, BookmarkReadStatus> categoryStatus) {
    List<CategoryReadStatusRes> result = new ArrayList<>();
    for (Map.Entry<Category, BookmarkReadStatus> entry : categoryStatus.entrySet()) {
      result.add(toCategoryReadStatusDto(entry.getKey(), entry.getValue()));
    }
    return result;
  }

  public BookmarkCrawlInfoRes toResponse(final BookmarkCrawlInfo info) {
    return new BookmarkCrawlInfoRes(info.getTitle(), info.getThumbnail());
  }

}
