package org.pickly.service.comment.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.pickly.service.comment.service.dto.CommentDTO;

@Getter
public class CommentRes {

  @Schema(description = "Comment ID", example = "1")
  private Long id;

  @Schema(description = "Comment를 추가한 Member 닉네임", example = "피클리마스터")
  private String member;

  @Schema(description = "Bookmark의 소유자의 Comment 여부", example = "true")
  private Boolean isOwnerComment;

  @Schema(description = "Comment 내용", example = "가슴이 웅장해지는 글이다..")
  private String content;

  @Schema(description = "Comment 생성일자. Unix timestamp 사용", example = "1678802356")
  private Long createdTimestamp;

  public CommentRes(CommentDTO dto) {
    this.id = dto.getId();
    this.member = dto.getMember();
    this.isOwnerComment = dto.getIsOwnerComment();
    this.content = dto.getContent();
    this.createdTimestamp = dto.getCreatedTimestamp();
  }

}
