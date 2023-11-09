package org.pickly.service.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.pickly.service.application.facade.BookmarkFacade;
import org.pickly.service.common.utils.encrypt.EncryptService;
import org.pickly.service.common.utils.encrypt.ExtensionKey;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.domain.bookmark.common.BookmarkMapper;
import org.pickly.service.domain.bookmark.dto.controller.request.BookmarkCreateReq;
import org.pickly.service.domain.bookmark.dto.controller.request.BookmarkUpdateReq;
import org.pickly.service.domain.bookmark.dto.controller.request.ExtensionBookmarkReq;
import org.pickly.service.domain.bookmark.dto.controller.response.BookmarkCrawlInfoRes;
import org.pickly.service.domain.bookmark.dto.controller.response.BookmarkReadStatusRes;
import org.pickly.service.domain.bookmark.dto.controller.response.BookmarkRes;
import org.pickly.service.domain.bookmark.dto.controller.response.CategoryReadStatusRes;
import org.pickly.service.domain.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.domain.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.bookmark.service.BookmarkWriteService;
import org.pickly.service.domain.bookmark.vo.BookmarkCrawlInfo;
import org.pickly.service.domain.bookmark.vo.BookmarkReadStatus;
import org.pickly.service.domain.category.entity.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Bookmark", description = "즐겨찾기 API")
public class BookmarkController {

  private final EncryptService encryptService;
  private final BookmarkReadService bookmarkReadService;
  private final BookmarkWriteService bookmarkWriteService;
  private final BookmarkFacade bookmarkFacade;
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
    return bookmarkReadService.countMemberLikes(memberId);
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
    return bookmarkReadService.findMemberLikeBookmarks(pageRequest, memberId);
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

      @Parameter(name = "loginId", description = "현재 로그인 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long loginId,

      @Parameter(name = "categoryId", description = "카테고리 ID 값. 필터링 필요 없으면 Null", example = "1")
      @RequestParam(required = false) final Long categoryId,

      @Parameter(name = "readByUser", description = "유저의 읽음 여부. 필터링 필요 없으면 Null", example = "true")
      @RequestParam(required = false) final Boolean readByUser,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "1")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    return bookmarkFacade.findMemberBookmarks(
        pageRequest, memberId, loginId, categoryId, readByUser
    );
  }

  @Operation(summary = "특정 북마크를 삭제한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
  })
  @DeleteMapping("/bookmarks/{bookmarkId}")
  public void deleteBookmark(
      @Parameter(name = "bookmarkId", description = "북마크 ID (PK) 값", example = "1", required = true)
      @Positive(message = "북마크 ID는 양수입니다.") @PathVariable final Long bookmarkId
  ) {
    bookmarkFacade.delete(bookmarkId);
  }

  @Operation(summary = "주어진 북마크 목록을 삭제한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
  })
  @DeleteMapping("/bookmarks/list")
  public void deleteBookmarks(
      @NotNull(message = "북마크 ID 리스트는 필수입니다.")
      @Size(min = 1, message = "북마크 ID 리스트는 최소 1개 이상이어야 합니다.")
      @RequestParam(value = "bookmarkId") List<@Positive(message = "북마크 ID는 양수입니다.") Long> bookmarkIds
  ) {
    bookmarkFacade.delete(bookmarkIds);
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
    bookmarkWriteService.like(bookmarkId);
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
    bookmarkWriteService.cancelLike(bookmarkId);
  }

  @PostMapping("/bookmarks")
  @Operation(summary = "북마크를 생성한다.")
  @ResponseStatus(HttpStatus.CREATED)
  public BookmarkRes create(
      @RequestBody
      @Valid
      BookmarkCreateReq dto
  ) {
    Bookmark entity = bookmarkFacade.create(dto);
    return bookmarkMapper.entityToResponseDto(entity);
  }

  @GetMapping("/bookmarks/{bookmarkId}")
  @Operation(summary = "특정 북마크를 조회한다.")
  public BookmarkRes getBookmarkById(
      @PathVariable
      @Positive(message = "북마크 ID는 양수입니다.")
      @Schema(description = "Bookmark id", example = "1")
      Long bookmarkId,

      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @RequestParam final Long memberId
  ) {
    Bookmark entity = bookmarkReadService.findByIdAndRead(bookmarkId, memberId);
    return bookmarkMapper.entityToResponseDto(entity);
  }

  @GetMapping("/members/bookmark/info")
  @Operation(summary = "특정 북마크의 제목과 썸네일을 url로부터 받아온다.")
  public BookmarkCrawlInfoRes getInfoFromUrl(
      @Parameter(name = "url", description = "북마크의 url", example = "http://naver.com", required = true)
      @NotEmpty(message = "북마크의 url을 입력해주세요.") @RequestParam final String url
  ) {
    BookmarkCrawlInfo info = bookmarkReadService.getInfoFromUrl(url);
    return bookmarkMapper.toResponse(info);
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

    return bookmarkReadService.findBookmarkByCategoryId(pageRequest, categoryId);
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
    bookmarkFacade.update(
        bookmarkId,
        bookmarkMapper.toUpdateDto(request)
    );
  }

  @PostMapping("/bookmarks/chrome-extension")
  @Operation(summary = "[크롬 익스텐션] 북마크를 생성한다.")
  @ResponseStatus(HttpStatus.CREATED)
  public BookmarkRes createForExtension(
      @RequestBody
      @Valid
      ExtensionBookmarkReq.CreateDto dto
  ) {
    ExtensionKey key = encryptService.getKey();
    BookmarkCreateReq createReq = new BookmarkCreateReq(key.decrypt(dto.memberId()), dto);
    Bookmark entity = bookmarkFacade.create(createReq);
    return bookmarkMapper.entityToResponseDto(entity);
  }

  @Operation(
      summary = "[크롬 익스텐션] 특정 유저의 북마크 목록을 전체 조회한다.",
      description = "hasNext = true인 경우, 다음 request의 cursorId는 직전 response의 마지막 요소의 ID. 필터링이 필요하지 않다면 queryParam null로!"
  )
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "성공")})
  @GetMapping("/members/bookmarks/chrome-extension")
  public PageResponse<BookmarkPreviewItemDTO> findMemberBookmarksForExtension(
      @Parameter(name = "memberId", description = "암호화된 유저 ID 값", example = "11a9892", required = true)
      @NotBlank(message = "유저 ID를 입력해주세요.") @RequestParam final String memberId,

      @Parameter(name = "categoryId", description = "카테고리 ID 값. 필터링 필요 없으면 Null", example = "1")
      @RequestParam(required = false) final Long categoryId,

      @Parameter(name = "readByUser", description = "유저의 읽음 여부. 필터링 필요 없으면 Null", example = "true")
      @RequestParam(required = false) final Boolean readByUser,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "1")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    ExtensionKey key = encryptService.getKey();
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    return bookmarkFacade.findMemberBookmarks(
        pageRequest, key.decrypt(memberId), key.decrypt(memberId), categoryId, readByUser
    );
  }

  @Operation(
      summary = "특정 유저의 전체 북마크 읽음 현황을 조회한다.",
      description = "북마크를 아직 하나도 추가하지 않은 경우, 0%로 처리한다."
  )
  @GetMapping("/members/{memberId}/bookmarks/read-status")
  public BookmarkReadStatusRes getBookmarkReadStatus(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    BookmarkReadStatus status = bookmarkFacade.getBookmarkReadStatus(memberId);
    return bookmarkMapper.toReadStatusDto(status);
  }

  @Operation(
      summary = "특정 유저의 특정 카테고리의 북마크 읽음 현황을 조회한다.",
      description = "북마크를 아직 하나도 추가하지 않은 경우, 0%로 처리한다."
  )
  @GetMapping("/members/{memberId}/categories/{categoryId}/bookmarks/read-status")
  public BookmarkReadStatusRes getBookmarkReadStatusByCategory(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @Parameter(name = "categoryId", description = "카테고리 ID 값", example = "1", required = true)
      @Positive(message = "카테고리 ID는 양수입니다.") @PathVariable final Long categoryId
  ) {
    BookmarkReadStatus status = bookmarkFacade.getBookmarkReadStatusByCategory(memberId, categoryId);
    return bookmarkMapper.toReadStatusDto(status);
  }

  @Operation(
      summary = "특정 유저의 전체 카테고리별 북마크 읽음 현황을 조회한다.",
      description = "북마크를 아직 하나도 추가하지 않은 경우, 0%로 처리한다."
  )
  @GetMapping("/members/{memberId}/categories/bookmarks/read-status")
  public List<CategoryReadStatusRes> getCategoryReadStatus(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    Map<Category, BookmarkReadStatus> status = bookmarkFacade.getCategoryReadStatus(memberId);
    return bookmarkMapper.toCategoryReadStatusDto(status);
  }

  @Operation(
      summary = "타이틀 값을 이용해 특정 유저의 북마크를 검색한다.",
      description = "검색 값이 제목에 포함되는 북마크를 반환한다. 대소문자를 구분하지 않는다."
  )
  @GetMapping("/members/{memberId}/bookmarks/search")
  public PageResponse<BookmarkPreviewItemDTO> searchBookmarks(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId,

      @Parameter(name = "keyword", description = "검색어", example = "ww0077", required = true)
      @RequestParam final String keyword,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "3")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    return bookmarkFacade.searchBookmarks(pageRequest, memberId, keyword);
  }

  @Operation(
      summary = "[크롬 익스텐션] 타이틀 값을 이용해 특정 유저의 북마크를 검색한다.",
      description = "검색 값이 제목에 포함되는 북마크를 반환한다. 대소문자를 구분하지 않는다."
  )
  @GetMapping("/members/bookmarks/search/chrome-extension")
  public PageResponse<BookmarkPreviewItemDTO> searchBookmarksForExtension(
      @Parameter(name = "memberId", description = "암호화된 유저 ID 값", example = "11a9892", required = true)
      @NotBlank(message = "유저 ID를 입력해주세요.") @RequestParam final String memberId,

      @Parameter(name = "keyword", description = "검색어", example = "ww0077", required = true)
      @RequestParam final String keyword,

      @Parameter(description = "커서 ID 값 :: default value = null", example = "3")
      @RequestParam(required = false) final Long cursorId,

      @Parameter(description = "한 페이지에 출력할 아이템 수 :: default value = 15", example = "10")
      @RequestParam(required = false) final Integer pageSize
  ) {
    ExtensionKey key = encryptService.getKey();
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    return bookmarkFacade.searchBookmarks(pageRequest, key.decrypt(memberId), keyword);
  }

}
