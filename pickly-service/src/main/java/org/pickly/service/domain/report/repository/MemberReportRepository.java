package org.pickly.service.domain.report.repository;

import org.pickly.service.domain.report.entity.MemberReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {

  boolean existsByReporterIdAndReportedId(Long reporterId, Long reportedId);
}
