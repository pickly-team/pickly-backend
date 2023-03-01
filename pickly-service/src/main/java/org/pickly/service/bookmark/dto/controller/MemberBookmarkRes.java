package org.pickly.service.bookmark.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;

@Getter
@Builder
@AllArgsConstructor
public class MemberBookmarkRes implements BaseBookmarkRes<BookmarkPreviewItemDTO>{

  @Schema(description = "다음 페이지가 남았는지?", example = "true")
  private boolean hasNext;

  @Schema(description = "현재 페이지의 아이템 리스트", implementation = BookmarkPreviewItemDTO.class)
  private List<BookmarkPreviewItemDTO> contents;

  private MemberBookmarkRes() {

  }

  public static MemberBookmarkRes from(final List<BookmarkPreviewItemDTO> contents, final boolean hasNext) {
    return MemberBookmarkRes.builder()
        .hasNext(hasNext)
        .contents(contents)
        .build();
  }

  @Override
  public List<BookmarkPreviewItemDTO> getContents() {
    return contents;
  }

  @Override
  public boolean hasNext() {
    return hasNext;
  }
}
