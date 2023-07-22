package org.pickly.service.comment.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import org.pickly.service.comment.entity.Comment;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Getter
public class CommentDTO {

  private Long id;
  private String member;
  private String bookmark;
  private String category;
  private Boolean isOwnerComment;
  private String content;
  private Long createdTimestamp;

  public CommentDTO(Comment comment) {
    ZonedDateTime kstDateTime = comment.getCreatedAt().atZone(ZoneOffset.of("+09:00"));
    long unixTimestamp = kstDateTime.toEpochSecond();

    this.id = comment.getId();
    this.member = comment.getMember().getNickname();
    this.bookmark = comment.getBookmark().getTitle();
    this.category = comment.getBookmark().getCategory().getName();
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdTimestamp = unixTimestamp;
  }

  @QueryProjection
  public CommentDTO(Comment comment, String member, String bookmark, String category) {
    ZonedDateTime kstDateTime = comment.getCreatedAt().atZone(ZoneOffset.of("+09:00"));
    long unixTimestamp = kstDateTime.toEpochSecond();

    this.id = comment.getId();
    this.member = member;
    this.bookmark = bookmark;
    this.category = category;
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdTimestamp = unixTimestamp;
  }

}
