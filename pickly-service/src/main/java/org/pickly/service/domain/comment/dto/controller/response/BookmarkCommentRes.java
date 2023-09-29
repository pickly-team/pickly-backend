package org.pickly.service.domain.comment.dto.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkCommentRes {

  @Schema(description = "Comment ID", example = "1")
  private Long id;

  @Schema(description = "Comment를 추가한 Member 닉네임", example = "피클리마스터")
  private String member;

  @Schema(description = "Comment를 추가한 Member id", example = "1")
  private Long memberId;

  @Schema(description = "Comment를 추가한 Member의 프로필 이모지", example = "😃")
  private String profileEmoji;

  @Schema(description = "Comment를 추가한 Bookmark 제목", example = "JS 기초 다루기")
  private String bookmark;

  @Schema(description = "Comment를 추가한 Bookmark의 Category", example = "TypeScript")
  private String category;

  @Schema(description = "Bookmark의 소유자의 Comment 여부", example = "true")
  private Boolean isOwnerComment;

  @Schema(description = "Comment 내용", example = "가슴이 웅장해지는 글이다..")
  private String content;

  @Schema(description = "Comment 생성일자. member timezone 기반의 Unix timestamp 사용", example = "1678802356")
  private Long createdTimestamp;

}
