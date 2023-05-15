package org.pickly.service.report.repository;

import org.pickly.service.report.entity.BookmarkReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkReportRepository extends JpaRepository<BookmarkReport, Long> {

  boolean existsByReporterIdAndReportedId(Long reporterId, Long reportedId);
}
