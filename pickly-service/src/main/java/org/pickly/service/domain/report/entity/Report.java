package org.pickly.service.domain.report.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.common.utils.base.BaseEntity;
import org.pickly.service.domain.member.entity.Member;

@Getter
@Entity
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Report extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_id")
  private Member reporter;

  @Column(length = 150, nullable = false)
  private String content;

  public abstract Object getReported();

  public void updateContent(String content) {
    this.content = content;
  }

}
