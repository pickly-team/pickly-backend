package org.pickly.service.bookmark.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.category.entity.Category;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.member.entity.Member;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

<<<<<<< HEAD
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(length = 500, nullable = false)
=======
  @Column(nullable = false, length = 2000)
>>>>>>> 8cc1e9675e2b35d4a6f3f0c5ec19c80b56b75161
  private String url;

  @Column(length = 100, nullable = false)
  private String title;

<<<<<<< HEAD
  @Column(length = 500, nullable = false)
=======

  @Column(name = "preview_image_url")
>>>>>>> 8cc1e9675e2b35d4a6f3f0c5ec19c80b56b75161
  private String previewImageUrl;

  @Column(name = "is_user_like", nullable = false)
  private Boolean isUserLike;

  @Column(name = "is_user_read", nullable = false)
  private Boolean isUserRead;

  @Column(nullable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private Visibility visibility;

  public void like() {
    this.isUserLike = true;
  }

  public void deleteLike() {
    this.isUserLike = false;
  }
}