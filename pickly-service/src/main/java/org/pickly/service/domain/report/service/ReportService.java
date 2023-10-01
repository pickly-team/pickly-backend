package org.pickly.service.domain.report.service;

import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.report.common.ReportType;
import org.pickly.service.domain.report.entity.Report;

import static org.pickly.service.domain.report.exception.ReportException.CannotReportSelfException;

public abstract class ReportService<T extends Report> {

  public abstract T create(Member reporter, Object reported, String content);

  public abstract ReportType getReportType();

  public void checkValidReport(Long fromMemberId, Long toMemberId) {
    if (fromMemberId.equals(toMemberId)) {
      throw new CannotReportSelfException();
    }
  }

}
