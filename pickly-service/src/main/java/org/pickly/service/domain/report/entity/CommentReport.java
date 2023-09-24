package org.pickly.service.domain.report.entity;

import jakarta.persistence.*;
import lombok.*;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.member.entity.Member;

@Entity
@Table(name = "comment_report")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_id")
  private Member reporter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_id")
  private Comment reported;

  @Column(length = 150, nullable = false)
  private String content;

  public static CommentReport create(Member reporter, Comment reported, String content) {
    return new CommentReport(reporter, reported, content);
  }

}
