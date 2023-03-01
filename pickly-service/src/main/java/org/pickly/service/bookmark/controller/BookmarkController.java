package org.pickly.service.bookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmarks")

public class BookmarkController {

  private final BookmarkService bookmarkService;

  // TODO : 멤버 아이디를 토큰에서 가져오도록 수정
  // 또는 북마크 작업하시는 분이 멤버 아이디를 추가해 주시면 추가할 것
  @PostMapping("/{bookmarkId}/like")
  @ResponseStatus(value = HttpStatus.OK)
  @Operation(summary = "Like bookmark", description = "Like bookmark")
  public void likeBookmark(
      @PathVariable
      @Positive(message = "북마크 ID는 양수입니다.")
      @Schema(description = "Bookmark id", example = "1")
      Long bookmarkId
  ) {
    bookmarkService.likeBookmark(bookmarkId);
  }


  @DeleteMapping("/{bookmarkId}/like")
  @ResponseStatus(value = HttpStatus.OK)
  @Operation(summary = "CancelLike bookmark", description = "CancelLike bookmark")
  public void cancelLikeBookmark(
      @PathVariable
      @Positive(message = "북마크 ID는 양수입니다.")
      @Schema(description = "Bookmark id", example = "1")
      Long bookmarkId
  ) {
    bookmarkService.cancelLikeBookmark(bookmarkId);
  }
}
