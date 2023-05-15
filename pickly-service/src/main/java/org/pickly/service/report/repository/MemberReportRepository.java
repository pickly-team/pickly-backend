package org.pickly.service.report.repository;

import org.pickly.service.report.entity.MemberReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {

  boolean existsByReporterIdAndReportedId(Long reporterId, Long reportedId);
}
