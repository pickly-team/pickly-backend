package org.pickly.service.bookmark.entity;

import jakarta.persistence.*;
import lombok.*;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(nullable = false, length = 2000)
  private String url;

  @Column(length = 100, nullable = false)
  private String title;

  @Column(name = "preview_image_url", nullable = false, length = 2000)
  private String previewImageUrl;

  @Column(name = "is_user_like", nullable = false)
  private Boolean isUserLike;

  @Column(name = "read_by_user", nullable = false)
  private Boolean readByUser;

  @Column(nullable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private Visibility visibility;

  public static Bookmark create(
      Category category, Member member, String url, String title,
      String previewImageUrl, Visibility visibility
  ) {
    return Bookmark.builder()
        .category(category)
        .member(member)
        .url(url)
        .title(title)
        .previewImageUrl(previewImageUrl)
        .isUserLike(false)
        .readByUser(false)
        .visibility(visibility)
        .build();
  }

  public void like() {
    this.isUserLike = true;
  }

  public void deleteLike() {
    this.isUserLike = false;
  }

  public void updateBookmark(Category category, String title,
      Boolean readByUser, Visibility visibility) {
    this.category = category;
    this.title = title;
    this.readByUser = readByUser;
    this.visibility = visibility;
  }
}