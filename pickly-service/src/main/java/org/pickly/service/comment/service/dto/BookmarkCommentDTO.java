package org.pickly.service.comment.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.common.utils.timezone.TimezoneHandler;

@Getter
public class BookmarkCommentDTO {

  private Long id;
  private String member;
  private Long memberId;
  private String profileEmoji;
  private String bookmark;
  private String category;
  private Boolean isOwnerComment;
  private String content;
  private Long createdTimestamp;

  public BookmarkCommentDTO(Comment comment) {
    this.id = comment.getId();
    this.member = comment.getMember().getNickname();
    this.profileEmoji = comment.getMember().getProfileEmoji();
    this.memberId = comment.getMember().getId();
    this.bookmark = comment.getBookmark().getTitle();
    this.category = comment.getBookmark().getCategory().getName();
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdTimestamp = TimezoneHandler.convertToUnix(comment.getCreatedAt());
  }

  @QueryProjection
  public BookmarkCommentDTO(Comment comment, String member, String bookmark, String category, String profileEmoji, Long memberId) {
    this.id = comment.getId();
    this.member = member;
    this.profileEmoji = profileEmoji;
    this.memberId = memberId;
    this.bookmark = bookmark;
    this.category = category;
    this.isOwnerComment = comment.getIsOwnerComment();
    this.content = comment.getContent();
    this.createdTimestamp = TimezoneHandler.convertToUnix(comment.getCreatedAt());
  }

}
