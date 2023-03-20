package org.pickly.service.comment.service.dto;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.Getter;
import org.pickly.service.comment.entity.Comment;

@Getter
public class CommentDTO {

  private Long id;
  private String member;
  private Boolean isOwnerComment;
  private String content;
  private Long createdTimestamp;

  public CommentDTO(Comment comment) {
    ZonedDateTime utcDateTime = comment.getCreatedAt().atZone(ZoneOffset.UTC);
    long unixTimestamp = utcDateTime.toEpochSecond();

    this.id = comment.getId();
    this.member = comment.getMember().getNickname();
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdTimestamp = unixTimestamp;
  }

}
