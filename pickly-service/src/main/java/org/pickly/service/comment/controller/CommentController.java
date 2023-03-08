package org.pickly.service.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.comment.common.CommentMapper;
import org.pickly.service.comment.controller.request.CommentCreateReq;
import org.pickly.service.comment.controller.response.CommentRes;
import org.pickly.service.comment.service.dto.CommentDTO;
import org.pickly.service.comment.service.interfaces.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

  private final CommentService commentService;
  private final CommentMapper commentMapper;

  @PostMapping("/bookmarks/{bookmarkId}/comment")
  @Operation(summary = "특정 Bookmark에 Comment 추가")
  public void create(
      @Parameter(name = "bookmarkId", description = "댓글을 추가할 Bookmark ID 값", example = "1", required = true)
      @Positive(message = "Bookmark ID는 양수입니다.") @PathVariable final Long bookmarkId,

      // TODO: JWT로 대체 예정
      @Parameter(name = "memberId", description = "댓글을 추가한 Member ID 값", example = "1", required = true)
      @Positive(message = "Member ID는 양수입니다.") @RequestParam final Long memberId,

      @Valid @RequestBody CommentCreateReq request
  ) {
    commentService.create(bookmarkId, memberId, request);
  }

  @GetMapping("/bookmarks/{bookmarkId}/comments")
  @Operation(summary = "특정 Bookmark의 Comment 전체 조회")
  public List<CommentRes> findByBookmark(
      @Parameter(name = "bookmarkId", description = "Bookmark ID 값", example = "1", required = true)
      @Positive(message = "Bookmark ID는 양수입니다.") @PathVariable final Long bookmarkId
  ) {
    List<CommentDTO> dtoList = commentService.findByBookmark(bookmarkId);
    return dtoList.stream()
        .map(commentMapper::toResponse)
        .toList();
  }

}
