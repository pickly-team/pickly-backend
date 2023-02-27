package org.pickly.service.bookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.dto.controller.MemberBookmarkRes;
import org.pickly.service.bookmark.dto.controller.MemberLikeBookmarkRes;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookmarkController {

  private final BookmarkService bookmarkService;

  @Operation(summary = "특정 유저의 좋아요 북마크 수 조회")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200", description = "성공",
          content = {
              @Content(
                  schema = @Schema(implementation = Long.class),
                  examples = @ExampleObject(value = "10")
              )
          }
      ),
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
          content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MemberLikeBookmarkRes.class)))
          })
  })
  @GetMapping("/members/{memberId}/bookmarks/likes")
  public MemberLikeBookmarkRes findMemberLikes(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @Parameter(name = "cursorId", description = "커서 ID 값. 디폴트 0", example = "1")
      @RequestParam(required = false, defaultValue = "0") final Long cursorId,

      @Parameter(name = "pageSize", description = "한 페이지에 나올 데이터 사이즈 값. 디폴트 10", example = "10")
      @RequestParam(required = false, defaultValue = "10") final Integer pageSize
  ) {
    return bookmarkService.findMemberLikeBookmarks(cursorId, memberId, pageSize);
  }

  @Operation(
      summary = "특정 유저의 북마크 전체 조회",
      description = "hasNext = true인 경우, 다음 request의 cursorId는 직전 response의 MAX(bookmarkId). 필터링이 필요하지 않다면 queryParam null로!"
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공",
          content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MemberBookmarkRes.class)))
          })
  })
  @GetMapping("/members/{memberId}/bookmarks")
  public MemberBookmarkRes findMemberBookmarks(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @Parameter(name = "categoryId", description = "카테고리 ID 값. 필터링 필요 없으면 Null", example = "1")
      @RequestParam(required = false) final Long categoryId,

      @Parameter(name = "isUserRead", description = "유저의 읽음 여부. 필터링 필요 없으면 Null", example = "true")
      @RequestParam(required = false) final Boolean isUserRead,

      @Parameter(name = "cursorId", description = "커서 ID 값. 디폴트 0", example = "1")
      @RequestParam(required = false, defaultValue = "0") final Long cursorId,

      @Parameter(name = "pageSize", description = "한 페이지에 나올 데이터 사이즈 값. 디폴트 10", example = "10")
      @RequestParam(required = false, defaultValue = "10") final Integer pageSize
  ) {
    return bookmarkService.findMemberBookmarks(cursorId, memberId, categoryId, isUserRead,
        pageSize);
  }


}
