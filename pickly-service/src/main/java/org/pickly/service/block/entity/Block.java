package org.pickly.service.block.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.member.entity.Member;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "block")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Block extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blockee_id")
  private Member blockee;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blocker_id")
  private Member blocker;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bookmark_id")
  private Bookmark bookmark;

  public Block(Member blocker, Member blockee) {
    this.blocker = blocker;
    this.blockee = blockee;
  }

  public Block(Member blocker, Bookmark bookmark) {
    this.blocker = blocker;
    this.bookmark = bookmark;
  }
}
