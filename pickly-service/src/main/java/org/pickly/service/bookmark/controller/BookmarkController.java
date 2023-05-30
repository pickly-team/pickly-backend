package org.pickly.service.bookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.bookmark.common.BookmarkMapper;
import org.pickly.service.bookmark.controller.request.BookmarkCreateReq;
import org.pickly.service.bookmark.controller.request.BookmarkUpdateReq;
import org.pickly.service.bookmark.controller.response.BookmarkDeleteRes;
import org.pickly.service.bookmark.controller.response.BookmarkListDeleteRes;
import org.pickly.service.bookmark.controller.response.BookmarkRes;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.bookmark.service.dto.BookmarkDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteResDTO;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "Bookmark", description = "즐겨찾기 API")
public class BookmarkController {

  private final BookmarkService bookmarkService;
  private final BookmarkMapper bookmarkMapper;

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  @GetMapping("/members/{memberId}/bookmarks/likes/count")
  @Operation(summary = "특정 유저의 좋아요 북마크 수를 조회한다.")
  public Long countMemberLikes(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    return bookmarkService.countMemberLikes(memberId);
  }

  @Operation(
      summary = "특정 유저가 좋아요한 북마크를 전체 조회한다.",
      description = "hasNext = true인 경우, 다음 request의 cursorId는 직전 response의 마지막 요소의 ID"
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

      @Parameter(description = "커서 ID 값 :: default value = null", example = "1")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    return bookmarkService.findMemberLikeBookmarks(pageRequest, memberId);
  }

  @Operation(
      summary = "특정 유저의 북마크 목록을 전체 조회한다.",
      description = "hasNext = true인 경우, 다음 request의 cursorId는 직전 response의 마지막 요소의 ID. 필터링이 필요하지 않다면 queryParam null로!"
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

      @Parameter(description = "커서 ID 값 :: default value = null", example = "1")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    return bookmarkService.findMemberBookmarks(
        pageRequest, memberId, categoryId, readByUser, visibility
    );
  }

  @Operation(summary = "특정 북마크를 삭제한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
  })
  @DeleteMapping("/bookmarks/{bookmarkId}")
  public BookmarkDeleteRes deleteBookmark(
      @Parameter(name = "bookmarkId", description = "북마크 ID (PK) 값", example = "1", required = true)
      @Positive(message = "북마크 ID는 양수입니다.") @PathVariable final Long bookmarkId
  ) {
    BookmarkDeleteResDTO bookmarkDeleteResDTO = bookmarkService.deleteBookmark(bookmarkId);
    return bookmarkMapper.toBookmarkDelete(bookmarkDeleteResDTO.getIsDeleted());
  }

  @Operation(summary = "주어진 북마크 목록을 삭제한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
  })
  @DeleteMapping("/bookmarks/list")
  public BookmarkListDeleteRes deleteBookmarks(
      @NotNull(message = "북마크 ID 리스트는 필수입니다.")
      @Size(min = 1, message = "북마크 ID 리스트는 최소 1개 이상이어야 합니다.")
      @RequestParam(value = "bookmarkId") List<@Positive(message = "북마크 ID는 양수입니다.") Long> bookmarkIds
  ) {
    BookmarkListDeleteResDTO bookmarkListDeleteResDTO = bookmarkService.deleteBookmarks(
        bookmarkIds);
    return bookmarkMapper.toBookmarkListDelete(bookmarkListDeleteResDTO.getIsDeleted());
  }

  // TODO : 멤버 아이디를 토큰에서 가져오도록 수정
  // 또는 북마크 작업하시는 분이 멤버 아이디를 추가해 주시면 추가할 것
  @PostMapping("/bookmarks/{bookmarkId}/like")
  @ResponseStatus(value = HttpStatus.OK)
  @Operation(summary = "북마크를 좋아요한다.")
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
  @Operation(summary = "북마크 좋아요를 취소한다.")
  public void cancelLikeBookmark(
      @PathVariable
      @Positive(message = "북마크 ID는 양수입니다.")
      @Schema(description = "Bookmark id", example = "1")
      Long bookmarkId
  ) {
    bookmarkService.cancelLikeBookmark(bookmarkId);
  }

  @PostMapping("/bookmarks")
  @Operation(summary = "북마크를 생성한다.")
  public ResponseEntity<BookmarkRes> create(
      @RequestBody
      @Valid
      BookmarkCreateReq dto
  ) {
    Bookmark entity = bookmarkService.create(dto);
    BookmarkRes response = bookmarkMapper.entityToResponseDto(entity);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

  @GetMapping("/bookmarks/{bookmarkId}")
  @Operation(summary = "특정 북마크를 조회한다.")
  public ResponseEntity<BookmarkRes> getBookmarkById(
      @PathVariable
      @Positive(message = "북마크 ID는 양수입니다.")
      @Schema(description = "Bookmark id", example = "1")
      Long bookmarkId
  ) {
    Bookmark entity = bookmarkService.findById(bookmarkId);
    BookmarkRes response = bookmarkMapper.entityToResponseDto(entity);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/categories/{categoryId}/bookmarks")
  @Operation(summary = "특정 카테고리에 속하는 북마크 목록을 조회한다.")
  public PageResponse<BookmarkItemDTO> getBookmarkByCategoryId(
      @PathVariable
      @Positive(message = "카테고리 ID는 양수입니다.")
      @Schema(description = "Category id", example = "1")
      Long categoryId,

      @Parameter
      @RequestBody
      PageRequest pageRequest
  ) {

    return bookmarkService.findBookmarkByCategoryId(pageRequest, categoryId);
  }

  @PutMapping("/bookmarks/{bookmarkId}")
  @Operation(summary = "특정 북마크를 수정한다.")
  public void updateBookmark(
      @PathVariable
      @Positive(message = "북마크 ID는 양수입니다.")
      @Schema(description = "Bookmark id", example = "1")
      Long bookmarkId,

      @Valid
      @RequestBody
      BookmarkUpdateReq request
  ) {
    bookmarkService.updateBookmark(bookmarkId,
        bookmarkMapper.toBookmarkUpdateReqDTO(request));
  }

}
