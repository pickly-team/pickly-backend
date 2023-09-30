package org.pickly.service.domain.bookmark.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.bookmark.service.dto.BookmarkInfoDTO;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
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

  @Builder
  public Bookmark(
      Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
      Category category, Member member, String url, String title,
      String previewImageUrl, Boolean isUserLike, Boolean readByUser,
      Visibility visibility
  ) {
    super(id, createdAt, updatedAt, deletedAt);
    this.category = category;
    this.member = member;
    this.url = url;
    this.title = title;
    this.previewImageUrl = previewImageUrl;
    this.isUserLike = isUserLike;
    this.readByUser = readByUser;
    this.visibility = visibility;
  }

  public static Bookmark create(
      Category category, Member member, String title,
      BookmarkInfoDTO info, Visibility visibility
  ) {
    return Bookmark.builder()
        .category(category)
        .member(member)
        .url(info.getUrl())
        .title(title)
        .previewImageUrl(info.getPreviewImageUrl())
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

  public void updateBookmark(
      Category category, String title,
      Boolean readByUser, Visibility visibility
  ) {
    this.category = category;
    this.title = title;
    this.readByUser = readByUser;
    this.visibility = visibility;
  }

}