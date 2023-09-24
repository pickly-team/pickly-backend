package org.pickly.service.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.pickly.service.application.facade.CommentFacade;
import org.pickly.service.domain.comment.common.CommentMapper;
import org.pickly.service.domain.comment.controller.request.CommentCreateReq;
import org.pickly.service.domain.comment.controller.request.CommentUpdateReq;
import org.pickly.service.domain.comment.controller.response.BookmarkCommentRes;
import org.pickly.service.domain.comment.controller.response.MemberCommentRes;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.comment.service.CommentReadService;
import org.pickly.service.domain.comment.service.dto.CommentDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Comment", description = "댓글 API")
public class CommentController {

  private final CommentReadService commentReadService;
  private final CommentFacade commentFacade;
  private final CommentMapper commentMapper;

  @GetMapping("/members/{memberId}/comments/count")
  @Operation(summary = "특정 유저의 댓글 수를 조회한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public Long countMemberComments(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    return commentReadService.countMemberComments(memberId);
  }

  @GetMapping("/members/{memberId}/comments")
  @Operation(summary = "특정 유저의 댓글을 전체 조회한다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "성공"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 ID"),
  })
  public List<MemberCommentRes> findByMember(
      @Parameter(name = "memberId", description = "유저 ID 값", example = "1", required = true)
      @Positive(message = "유저 ID는 양수입니다.") @PathVariable final Long memberId
  ) {
    List<CommentDTO> dtoList = commentReadService.findByMember(memberId);
    return dtoList.stream()
        .map(commentMapper::toMemberCommentsResponse)
        .toList();
  }

  @PostMapping("/bookmarks/{bookmarkId}/comment")
  @Operation(summary = "특정 북마크에 댓글을 추가한다.")
  public BookmarkCommentRes create(
      @Parameter(name = "bookmarkId", description = "댓글을 추가할 Bookmark ID 값", example = "1", required = true)
      @Positive(message = "Bookmark ID는 양수입니다.") @PathVariable final Long bookmarkId,

      // TODO: JWT로 대체 예정
      @Parameter(name = "memberId", description = "댓글을 추가한 Member ID 값", example = "1", required = true)
      @Positive(message = "Member ID는 양수입니다.") @RequestParam final Long memberId,

      @Valid @RequestBody CommentCreateReq request
  ) {
    Comment comment = commentFacade.create(
        bookmarkId, memberId, commentMapper.toCreateDTO(request)
    );
    return commentMapper.toResponse(comment, memberId);
  }

  @GetMapping("/bookmarks/{bookmarkId}/comments")
  @Operation(summary = "특정 북마크에 달린 댓글을 전체 조회한다.")
  public List<BookmarkCommentRes> findByBookmark(
      @Parameter(name = "bookmarkId", description = "Bookmark ID 값", example = "1", required = true)
      @Positive(message = "Bookmark ID는 양수입니다.") @PathVariable final Long bookmarkId,

      @Parameter(name = "memberId", description = "댓글을 조회하는 Member ID 값", example = "1", required = true)
      @Positive(message = "Member ID는 양수입니다.") @RequestParam final Long memberId
  ) {
    List<CommentDTO> dtoList = commentReadService.findByBookmark(bookmarkId);
    return dtoList.stream()
        .map(dto -> commentMapper.toBookmarkCommentsResponse(dto, memberId))
        .toList();
  }

  // TODO: JWT로 로그인 유저가 해당 comment 삭제 권한 있는지 체크하는 로직 추가 예정
  @DeleteMapping("/comments/{commentId}")
  @Operation(summary = "특정 댓글을 삭제한다.")
  public void delete(
      @Parameter(name = "commentId", description = "Comment ID 값", example = "1", required = true)
      @Positive(message = "Comment ID는 양수입니다.") @PathVariable final Long commentId
  ) {
    commentFacade.delete(commentId);
  }

  // TODO: JWT로 로그인 유저가 해당 comment 삭제 권한 있는지 체크하는 로직 추가 예정
  @PutMapping("/comments/{commentId}")
  @Operation(summary = "특정 댓글을 수정한다.")
  public BookmarkCommentRes updateComment(
      @Parameter(name = "commentId", description = "Comment ID 값", example = "1", required = true)
      @Positive(message = "Comment ID는 양수입니다.") @PathVariable final Long commentId,

      @Parameter(name = "memberId", description = "댓글을 수정하는 Member ID 값", example = "1", required = true)
      @Positive(message = "Member ID는 양수입니다.") @RequestParam final Long memberId,

      @Valid @RequestBody CommentUpdateReq request
  ) {
    Comment comment = commentFacade.update(commentId, memberId, commentMapper.toUpdateDTO(request));
    return commentMapper.toResponse(comment, memberId);
  }

}
