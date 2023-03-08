package org.pickly.service.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.comment.controller.request.CommentCreateReq;
import org.pickly.service.comment.service.interfaces.CommentService;
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

}
