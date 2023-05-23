package org.pickly.service.report.service;

import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.BusinessException;
import org.pickly.common.error.exception.ErrorCode;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.report.entity.BookmarkReport;
import org.pickly.service.report.repository.BookmarkReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkReportService {

  private final BookmarkReportRepository bookmarkReportRepository;
  private final MemberService memberService;
  private final BookmarkService bookmarkService;

  public void reportBookmark(Long reporterId, Long reportedId, String content) {
    if (bookmarkReportRepository.existsByReporterIdAndReportedId(reporterId, reportedId)) {
      throw new BusinessException("이미 신고한 북마크입니다.", ErrorCode.ENTITY_CONFLICT);
    }

    Member reporter = memberService.findById(reporterId);
    Bookmark bookmark = bookmarkService.findById(reportedId);

    if (reporter.equals(bookmark.getMember())) {
      throw new BusinessException("자신의 북마크는 신고할 수 없습니다.", ErrorCode.INVALID_INPUT_VALUE);
    }

    bookmarkReportRepository.save(
        BookmarkReport.builder()
            .reporter(reporter)
            .reported(bookmark)
            .content(content)
            .build()
    );
  }
}
