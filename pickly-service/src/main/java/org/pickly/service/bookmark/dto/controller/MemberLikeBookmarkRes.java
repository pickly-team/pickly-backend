package org.pickly.service.bookmark.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;

@Getter
@Builder
@AllArgsConstructor
public class MemberLikeBookmarkRes implements BaseBookmarkRes<BookmarkItemDTO>{

  @Schema(description = "다음 페이지가 남았는지?", example = "true")
  private boolean hasNext;

  @Schema(description = "현재 페이지의 아이템 리스트", implementation = BookmarkItemDTO.class)
  private List<BookmarkItemDTO> contents;


  private MemberLikeBookmarkRes() {

  }

  public static MemberLikeBookmarkRes from(final List<BookmarkItemDTO> contents, final boolean hasNext) {
    return MemberLikeBookmarkRes.builder()
        .hasNext(hasNext)
        .contents(contents)
        .build();
  }

  @Override
  public List<BookmarkItemDTO> getContents() {
    return contents;
  }

  @Override
  public boolean hasNext() {
    return hasNext;
  }

}
