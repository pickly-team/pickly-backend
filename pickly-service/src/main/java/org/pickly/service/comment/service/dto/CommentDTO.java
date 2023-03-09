package org.pickly.service.comment.service.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import org.pickly.service.comment.entity.Comment;

@Getter
public class CommentDTO {

  private Long id;
  private String member;
  private Boolean isOwnerComment;
  private String content;
  private LocalDateTime createdDateTime;

  public CommentDTO(Comment comment) {
    this.id = comment.getId();
    this.member = comment.getMember().getNickname();
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdDateTime = comment.getCreatedAt();
  }

}
