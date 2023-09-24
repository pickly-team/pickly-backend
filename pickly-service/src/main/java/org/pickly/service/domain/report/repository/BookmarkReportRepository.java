package org.pickly.service.domain.report.repository;

import org.pickly.service.domain.report.entity.BookmarkReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkReportRepository extends JpaRepository<BookmarkReport, Long> {

  boolean existsByReporterIdAndReportedId(Long reporterId, Long reportedId);
}
