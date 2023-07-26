package org.pickly.service.report.repository;

import org.pickly.service.report.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

  boolean existsByReporterIdAndReportedId(Long reporterId, Long reportedId);

}
