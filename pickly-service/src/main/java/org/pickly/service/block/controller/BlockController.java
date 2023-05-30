package org.pickly.service.block.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.block.common.BlockMapper;
import org.pickly.service.block.controller.response.BlockBookmarkRes;
import org.pickly.service.block.controller.response.BlockMemberRes;
import org.pickly.service.block.service.BlockService;
import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
@Tag(name = "Block", description = "차단 ( 유저 / 북마크 ) API")
public class BlockController {

  private final BlockService blockService;

  /*
   * 2023 / 04 / 09
   * TODO => 전략 or 데코레이터 패턴으로 추후 리펙토링 논의
   */

  /*
   * User Block
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
    blockService.blockMember(blockerId, blockeeId);
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
    blockService.unBlockMember(blockerId, blockeeId);
  }

  @GetMapping("/member/blocks/{blockerId}")
  @Operation(summary = "차단한 유저 목록을 조회한다.")
  public BlockMemberRes getBlockedMembers(
      @Parameter(name = "blockerId", description = "유저 차단조회를 위한 대상 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.")
      @PathVariable final Long blockerId,

      @Parameter(name = "cursor", description = "마지막 끝의 Id", example = "1", required = true) final @RequestParam Long cursorId,

      @Parameter(name = "size", description = "페이지 사이즈", example = "1", required = true) final @RequestParam Integer size
  ) {
    List<BlockMemberDTO> blockedMembers = blockService.getBlockedMembers(blockerId, cursorId, size);

    return BlockMapper.toMember(blockedMembers);
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
    blockService.blockBookmark(blockerId, bookmarkId);
  }

  @DeleteMapping("/bookmark/{blockerId}/block/{bookmarkId}")
  @Operation(summary = "북마크를 차단 해제한다.")
  public void unBlockBookmark(
      @Parameter(name = "blockerId", description = "차단해제 요청을 보낸 유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long blockerId,

      @Parameter(name = "bookmarkId", description = "차단 해제를 할 북마크 ID 값", example = "2", required = true)
      @Positive(message = "북마크 ID는 양수입니다.") @PathVariable final Long bookmarkId
  ) {
    blockService.unBlockBookmark(blockerId, bookmarkId);
  }

  @GetMapping("/bookmark/blocks/{blockerId}")
  @Operation(summary = "차단된 북마크 목록을 조회한다.")
  public BlockBookmarkRes getBlockedBookmarks(
      @Parameter(name = "blockerId", description = "북마크 차단조회를 위한 대상 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long blockerId,

      @Parameter(name = "page", description = "페이지 번호", example = "1", required = true) final @RequestParam Long cursorId,
      @Parameter(name = "size", description = "페이지 사이즈", example = "1", required = true) final @RequestParam Integer size
  ) {
    List<BlockBookmarkDTO> blockedBookmarks = blockService.getBlockedBookmarks(blockerId, cursorId,
        size);

    return BlockMapper.toBookmark(blockedBookmarks);
  }
}
