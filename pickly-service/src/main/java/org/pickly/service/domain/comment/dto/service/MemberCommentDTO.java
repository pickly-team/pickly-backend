package org.pickly.service.domain.comment.dto.service;

import lombok.Getter;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.member.entity.Member;

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
    Member author = comment.getMember();
    this.id = comment.getId();
    this.member = author.getNickname();
    this.profileEmoji = author.getProfileEmoji();
    this.bookmark = comment.getBookmark().getTitle();
    this.category = comment.getBookmark().getCategory().getName();
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdTimestamp = TimezoneHandler.convertToUnix(comment.getCreatedAt(), author.getTimezone());
  }
}
