package org.pickly.service.domain.report.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "bookmark_report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkReport extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_id")
  private Member reporter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_id")
  private Bookmark reported;

  @Column(length = 150, nullable = false)
  private String content;

  @Builder
  public BookmarkReport(
      Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
      Member reporter, Bookmark reported, String content
  ) {
    super(id, createdAt, updatedAt, deletedAt);
    this.reporter = reporter;
    this.reported = reported;
    this.content = content;
  }

  public static BookmarkReport create(Member reporter, Bookmark reported, String content) {
    return BookmarkReport.builder()
        .reporter(reporter).reported(reported).content(content)
        .build();
  }
}
