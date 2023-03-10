package org.pickly.service.bookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.bookmark.common.BookmarkMapper;
import org.pickly.service.bookmark.controller.request.BookmarkDeleteReq;
import org.pickly.service.bookmark.controller.request.BookmarkDeleteRes;
import org.pickly.service.bookmark.controller.request.BookmarkListDeleteReq;
import org.pickly.service.bookmark.controller.request.BookmarkListDeleteRes;
import org.pickly.service.bookmark.common.BookmarkMapper;
import org.pickly.service.bookmark.controller.request.BookmarkListDeleteReq;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.bookmark.service.dto.BookmarkDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteResDTO;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookmarkController {

  private final BookmarkService bookmarkService;
  private final BookmarkMapper bookmarkMapper;

  @Operation(summary = "특정 유저의 좋아요 북마크 수 조회")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  @GetMapping("/members/{memberId}/bookmarks/likes/count")
  public Long countMemberLikes(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    return bookmarkService.countMemberLikes(memberId);
  }

  @Operation(
      summary = "특정 유저가 좋아요한 북마크 전체 조회",
      description = "hasNext = true인 경우, 다음 request의 cursorId는 직전 response의 MAX(bookmarkId)"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = PageResponse.class)))
  })
  @GetMapping("/members/{memberId}/bookmarks/likes")
  public PageResponse<BookmarkItemDTO> findMemberLikes(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,
      @Parameter @RequestBody PageRequest pageRequest
  ) {
    return bookmarkService.findMemberLikeBookmarks(pageRequest, memberId);
  }

  @Operation(
      summary = "특정 유저의 북마크 전체 조회",
      description = "hasNext = true인 경우, 다음 request의 cursorId는 직전 response의 MAX(bookmarkId). 필터링이 필요하지 않다면 queryParam null로!"
  )
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "성공")})
  @GetMapping("/members/{memberId}/bookmarks")
  public PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @Parameter(name = "categoryId", description = "카테고리 ID 값. 필터링 필요 없으면 Null", example = "1")
      @RequestParam(required = false) final Long categoryId,

      @Parameter(name = "readByUser", description = "유저의 읽음 여부. 필터링 필요 없으면 Null", example = "true")
      @RequestParam(required = false) final Boolean readByUser,

      @Parameter(name = "visibility", description = "북마크 공개 범위", example = "SCOPE_PUBLIC")
      @RequestParam(required = false) final Visibility visibility,

      @Parameter @RequestBody PageRequest pageRequest
  ) {
    return bookmarkService.findMemberBookmarks(pageRequest, memberId, categoryId, readByUser,
        visibility);
  }

  @Operation(summary = "특정 북마크 삭제")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PutMapping("/bookmarks")
  public BookmarkDeleteRes deleteBookmark(
      @RequestBody
      BookmarkDeleteReq request
  ) {
    BookmarkDeleteResDTO bookmarkDeleteResDTO = bookmarkService.deleteBookmark(
        request.getBookmarkId());
    return bookmarkMapper.toBookmarkDelete(bookmarkDeleteResDTO.getIsDeleted());
  }

  @Operation(summary = "북마크 리스트 삭제")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
  })
  @PutMapping("/bookmarks/list")
  public BookmarkListDeleteRes deleteBookmarks(
      @RequestBody
      BookmarkListDeleteReq request
  ) {
    BookmarkListDeleteResDTO bookmarkListDeleteResDTO = bookmarkService.deleteBookmarks(
        request.getBookmarkIds());
    return bookmarkMapper.toBookmarkListDelete(bookmarkListDeleteResDTO.getIsDeleted());
  }

  // TODO : 멤버 아이디를 토큰에서 가져오도록 수정
  // 또는 북마크 작업하시는 분이 멤버 아이디를 추가해 주시면 추가할 것
  @PostMapping("/bookmarks/{bookmarkId}/like")
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

  @DeleteMapping("/bookmarks/{bookmarkId}/like")
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
