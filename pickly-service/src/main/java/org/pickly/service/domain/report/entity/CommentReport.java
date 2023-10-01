package org.pickly.service.domain.report.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.member.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport extends Report {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_id")
  private Comment reported;

  @Override
  public Comment getReported() {
    return this.reported;
  }

  @Builder
  public CommentReport(
      Member reporter, Comment reported, String content
  ) {
    super(reporter, content);
    this.reported = reported;
  }

  public static CommentReport create(Member reporter, Comment reported, String content) {
    return CommentReport.builder()
        .reporter(reporter).reported(reported).content(content)
        .build();
  }

}
