package org.pickly.service.category.entity;

import jakarta.persistence.Column;
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
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.member.entity.Member;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "is_auto_delete_mode", nullable = false)
  private Boolean isAutoDeleteMode;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(columnDefinition = "text")
  private String emoji;

  public Category update(Boolean isAutoDeleteMode, String name, String emoji) {
    this.isAutoDeleteMode = isAutoDeleteMode;
    this.name = name;
    this.emoji = emoji;

    return this;
  }
}
