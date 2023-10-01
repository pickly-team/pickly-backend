package org.pickly.service.domain.report.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.member.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkReport extends Report {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_id")
  private Bookmark reported;

  @Override
  public Bookmark getReported() {
    return this.reported;
  }

  @Builder
  public BookmarkReport(
      Member reporter, Bookmark reported, String content
  ) {
    super(reporter, content);
    this.reported = reported;
  }

  public static BookmarkReport create(Member reporter, Bookmark reported, String content) {
    return BookmarkReport.builder()
        .reporter(reporter).reported(reported).content(content)
        .build();
  }
}
