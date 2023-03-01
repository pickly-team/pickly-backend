package org.pickly.service.bookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmarks")

public class BookmarkController {

  private final BookmarkService bookmarkService;


  @PutMapping("/{bookmarkId}/like")
  @Operation(summary = "Like bookmark", description = "Like bookmark")

  // TODO : 멤버 아이디를 토큰에서 가져오도록 수정
  // 또는 북마크 작업하시는 분이 멤버 아이디를 추가해 주시면 추가할 것
  public ResponseEntity<Void> likeBookmark(
      @PathVariable
      @Schema(description = "Bookmark id", example = "1")
      Long bookmarkId
  ) {
    bookmarkService.likeBookmark(bookmarkId);
    return ResponseEntity.ok().build();
  }


  @DeleteMapping("/{bookmarkId}/like")
  @Operation(summary = "CancelLike bookmark", description = "CancelLike bookmark")
  public ResponseEntity<Void> cancelLikeBookmark(
      @PathVariable
      @Schema(description = "Bookmark id", example = "1")
      Long bookmarkId
  ) {
    bookmarkService.cancelLikeBookmark(bookmarkId);
    return ResponseEntity.ok().build();
  }
}
