package org.pickly.service.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.application.facade.BlockFacade;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.domain.block.common.BlockMapper;
import org.pickly.service.domain.block.dto.controller.response.BlockBookmarkRes;
import org.pickly.service.domain.block.dto.controller.response.BlockMemberRes;
import org.pickly.service.domain.block.service.BlockReadService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Block", description = "차단 ( 유저 / 북마크 ) API")
public class BlockController {

  private final BlockReadService blockReadService;
  private final BlockFacade blockFacade;

  /*
   * 2023 / 04 / 09
   * TODO => 전략 or 데코레이터 패턴으로 추후 리펙토링 논의
   */

  @PostMapping("/member/{blockerId}/block/{blockeeId}")
  @Operation(summary = "유저를 차단한다.")
  public void blockMember(
      @Parameter(name = "blockerId", description = "차단 요청을 보낸 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.")
      @PathVariable final Long blockerId,

      @Parameter(name = "blockeeId", description = "차단 당한 유저 ID 값", example = "2", required = true)
      @Positive(message = "유저 ID는 양수입니다.")
      @PathVariable final Long blockeeId
  ) {
    blockFacade.blockMember(blockerId, blockeeId);
  }

  @DeleteMapping("/member/{blockerId}/block/{blockeeId}")
  @Operation(summary = "유저 차단을 해제한다.")
  public void unBlockMember(
      @Parameter(name = "blockerId", description = "차단해제 요청을 보낸 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.")
      @PathVariable final Long blockerId,

      @Parameter(name = "blockeeId", description = "차단 해제를 할 유저 ID 값", example = "2", required = true)
      @Positive(message = "유저 ID는 양수입니다.")
      @PathVariable final Long blockeeId
  ) {
    blockFacade.unblockMember(blockerId, blockeeId);
  }

  @GetMapping("/member/blocks/{blockerId}")
  @Operation(summary = "차단한 유저 목록을 조회한다.")
  public PageResponse<BlockMemberRes> getBlockedMembers(
      @Parameter(name = "blockerId", description = "유저 차단조회를 위한 대상 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.")
      @PathVariable final Long blockerId,

      @Parameter(name = "cursorId", description = "마지막 끝의 Id", example = "1") final @RequestParam(required = false) String cursorId,

      @Parameter(name = "pageSize", description = "페이지 사이즈", example = "1") final @RequestParam(defaultValue = "15") Integer pageSize
  ) {
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);
    List<BlockMemberRes> blockedMembers = blockReadService.getBlockedMembers(blockerId, pageRequest)
        .stream()
        .map(BlockMapper::toMember)
        .toList();

    PageResponse<BlockMemberRes> response = new PageResponse<>(
        blockedMembers.size(),
        pageRequest.getPageSize(), blockedMembers);

    response.removeElement(pageRequest.getPageSize());

    return response;
  }


  /*
   * Bookmark Block
   */
  @PostMapping("/bookmark/{blockerId}/block/{bookmarkId}")
  @Operation(summary = "북마크를 차단한다.")
  public void blockBookmark(
      @Parameter(name = "blockerId", description = "차단 요청을 보낸 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.")
      @PathVariable final Long blockerId,

      @Parameter(name = "bookmarkId", description = "차단 당한 북마크 ID 값", example = "2", required = true)
      @Positive(message = "북마크 ID는 양수입니다.")
      @PathVariable final Long bookmarkId
  ) {
    blockFacade.blockBookmark(blockerId, bookmarkId);
  }

  @DeleteMapping("/bookmark/{blockerId}/block/{bookmarkId}")
  @Operation(summary = "북마크를 차단 해제한다.")
  public void unBlockBookmark(
      @Parameter(name = "blockerId", description = "차단해제 요청을 보낸 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long blockerId,

      @Parameter(name = "bookmarkId", description = "차단 해제를 할 북마크 ID 값", example = "2", required = true)
      @Positive(message = "북마크 ID는 양수입니다.") @PathVariable final Long bookmarkId
  ) {
    blockFacade.unblockBookmark(blockerId, bookmarkId);
  }

  @GetMapping("/bookmark/blocks/{blockerId}")
  @Operation(summary = "차단된 북마크 목록을 조회한다.")
  public PageResponse<BlockBookmarkRes> getBlockedBookmarks(
      @Parameter(name = "blockerId", description = "북마크 차단조회를 위한 대상 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long blockerId,

      @Parameter(name = "cursorId", description = "마지막 member의 Id", example = "1")
      @RequestParam(required = false) final String cursorId,

      @Parameter(name = "pageSize", description = "페이지 사이즈", example = "1")
      @RequestParam(defaultValue = "15") final Integer pageSize
  ) {
    PageRequest pageRequest = new PageRequest(cursorId, pageSize);

    List<BlockBookmarkRes> blockedBookmarks = blockReadService.getBlockedBookmarks(blockerId, pageRequest)
        .stream()
        .map(BlockMapper::toBookmark)
        .toList();

    PageResponse<BlockBookmarkRes> response = new PageResponse<>(
        blockedBookmarks.size(),
        pageRequest.getPageSize(), blockedBookmarks);

    response.removeElement(pageRequest.getPageSize());

    return response;
  }
}
