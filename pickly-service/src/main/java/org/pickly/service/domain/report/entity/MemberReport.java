package org.pickly.service.domain.report.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.domain.member.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReport extends Report {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_id")
  private Member reported;

  @Override
  public Member getReported() {
    return this.reported;
  }

  @Builder
  public MemberReport(
      Member reporter, Member reported, String content
  ) {
    super(reporter, content);
    this.reported = reported;
  }

  public static MemberReport create(Member reporter, Member reported, String content) {
    return MemberReport.builder()
        .reporter(reporter).reported(reported).content(content)
        .build();
  }

}
