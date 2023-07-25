package org.pickly.service.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.comment.common.CommentMapper;
import org.pickly.service.comment.controller.request.CommentCreateReq;
import org.pickly.service.comment.controller.request.CommentUpdateReq;
import org.pickly.service.comment.controller.response.BookmarkCommentRes;
import org.pickly.service.comment.controller.response.MemberCommentRes;
import org.pickly.service.comment.service.dto.CommentDTO;
import org.pickly.service.comment.service.interfaces.CommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Comment", description = "댓글 API")
public class CommentController {

  private final CommentService commentService;
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
    return commentService.countMemberComments(memberId);
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
    List<CommentDTO> dtoList = commentService.findByMember(memberId);
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
    CommentDTO dto = commentService.create(bookmarkId, memberId,
        commentMapper.toCreateDTO(request));
    return commentMapper.toBookmarkCommentsResponse(dto, memberId);
  }

  @GetMapping("/bookmarks/{bookmarkId}/comments")
  @Operation(summary = "특정 북마크에 달린 댓글을 전체 조회한다.")
  public List<BookmarkCommentRes> findByBookmark(
      @Parameter(name = "bookmarkId", description = "Bookmark ID 값", example = "1", required = true)
      @Positive(message = "Bookmark ID는 양수입니다.") @PathVariable final Long bookmarkId,

      @Parameter(name = "memberId", description = "댓글을 조회하는 Member ID 값", example = "1", required = true)
      @Positive(message = "Member ID는 양수입니다.") @RequestParam final Long memberId
  ) {
    List<CommentDTO> dtoList = commentService.findByBookmark(bookmarkId);
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
    commentService.delete(commentId);
  }

  // TODO: JWT로 로그인 유저가 해당 comment 삭제 권한 있는지 체크하는 로직 추가 예정
  @PutMapping("/comments/{commentId}")
  @Operation(summary = "특정 댓글을 수정한다.")
  public BookmarkCommentRes updateComment(
      @Parameter(name = "commentId", description = "Comment ID 값", example = "1", required = true)
      @Positive(message = "Comment ID는 양수입니다.") @PathVariable final Long commentId,

      @Valid @RequestBody CommentUpdateReq request
  ) {
    CommentDTO dto = commentService.update(commentId, commentMapper.toUpdateDTO(request));
    return commentMapper.toBookmarkCommentsResponse(dto);
  }

}
