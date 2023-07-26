package org.pickly.service.comment.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.member.entity.Member;

@Getter
public class CommentDTO {

  private Long id;
  private String member;
  private Long memberId;
  private String profileEmoji;
  private Long bookmarkId;
  private String bookmark;
  private String category;
  private Boolean isOwnerComment;
  private String content;
  private Long createdTimestamp;

  public CommentDTO(Comment comment) {
    String timezone = comment.getMember().getTimezone();
    this.id = comment.getId();
    this.member = comment.getMember().getNickname();
    this.profileEmoji = comment.getMember().getProfileEmoji();
    this.memberId = comment.getMember().getId();
    this.bookmarkId = comment.getBookmark().getId();
    this.bookmark = comment.getBookmark().getTitle();
    this.category = comment.getBookmark().getCategory().getName();
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdTimestamp = TimezoneHandler.convertToUnix(comment.getCreatedAt(), timezone);
  }

  @QueryProjection
  public CommentDTO(Comment comment, Member member, String bookmark, String category, Long bookmarkId) {
    String timezone = member.getTimezone();
    this.id = comment.getId();
    this.member = member.getNickname();
    this.profileEmoji = member.getProfileEmoji();
    this.bookmarkId = bookmarkId;
    this.memberId = member.getId();
    this.bookmark = bookmark;
    this.category = category;
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdTimestamp = TimezoneHandler.convertToUnix(comment.getCreatedAt(), timezone);
  }

}
