package org.pickly.service.comment.service.dto;

import lombok.Getter;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.common.utils.timezone.TimezoneHandler;

@Getter
public class MemberCommentDTO {

  private Long id;
  private String member;
  private String profileEmoji;
  private String bookmark;
  private String category;
  private Boolean isOwnerComment;
  private String content;
  private Long createdTimestamp;

  public MemberCommentDTO(Comment comment) {
    this.id = comment.getId();
    this.member = comment.getMember().getNickname();
    this.profileEmoji = comment.getMember().getProfileEmoji();
    this.bookmark = comment.getBookmark().getTitle();
    this.category = comment.getBookmark().getCategory().getName();
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdTimestamp = TimezoneHandler.convertToUnix(comment.getCreatedAt());
  }
}
