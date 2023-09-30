package org.pickly.service.domain.report.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "member_report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReport extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_id")
  private Member reporter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_id")
  private Member reported;

  @Column(length = 150, nullable = false)
  private String content;

  @Builder
  public MemberReport(
      Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
      Member reporter, Member reported, String content
  ) {
    super(id, createdAt, updatedAt, deletedAt);
    this.reporter = reporter;
    this.reported = reported;
    this.content = content;
  }

  public static MemberReport create(Member reporter, Member reported, String content) {
    return MemberReport.builder()
        .reporter(reporter).reported(reported).content(content)
        .build();
  }
}
