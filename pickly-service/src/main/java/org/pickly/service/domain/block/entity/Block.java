package org.pickly.service.domain.block.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.member.entity.Member;

@Entity
@Getter
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

  @Builder
  Block(Member blocker, Member blockee, Bookmark bookmark) {
    this.blocker = blocker;
    this.blockee = blockee;
    this.bookmark = bookmark;
  }

}
