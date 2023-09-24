package org.pickly.service.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.member.entity.Member;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "bookmark_id")
  private Bookmark bookmark;

  @Column(name = "is_owner_comment", nullable = false)
  private Boolean isOwnerComment;

  @Column(length = 150, nullable = false)
  private String content;

  public static Comment create(Member member, Bookmark bookmark, String content) {
    Boolean isOwnerComment = member.equals(bookmark.getMember());
    return new Comment(member, bookmark, isOwnerComment, content);
  }

  public void updateContent(final String content) {
    this.content = content;
  }

}
