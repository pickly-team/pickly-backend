package org.pickly.service.domain.category.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.member.entity.Member;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

  public Category update(String name, String emoji) {
    this.name = name;
    this.emoji = emoji;
    return this;
  }

}
