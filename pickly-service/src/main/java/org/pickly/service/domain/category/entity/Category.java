package org.pickly.service.domain.category.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(length = 10, nullable = false)
  private String name;

  @Column(columnDefinition = "text")
  private String emoji;

  @Column(name = "order_num", nullable = false)
  private Integer orderNum;

  @Builder
  Category(
      Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
      Member member, String name, String emoji, Integer orderNum
  ) {
    super(id, createdAt, updatedAt, deletedAt);
    this.member = member;
    this.name = name;
    this.emoji = emoji;
    this.orderNum = orderNum;
  }

  public Category update(String name, String emoji) {
    this.name = name;
    this.emoji = emoji;
    return this;
  }

}
