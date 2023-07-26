package org.pickly.service.report.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.service.common.error.exception.BusinessException
import org.pickly.service.bookmark.BookmarkFactory
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository
import org.pickly.service.category.CategoryFactory
import org.pickly.service.category.repository.interfaces.CategoryRepository
import org.pickly.service.member.MemberFactory
import org.pickly.service.member.repository.interfaces.MemberRepository
import org.pickly.service.report.repository.BookmarkReportRepository
import org.pickly.service.report.repository.MemberReportRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers
import spock.lang.Specification

@Transactional
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BookmarkReportServiceSpec extends Specification {

    @Autowired
    private BookmarkReportService bookmarkReportService

    @Autowired
    private BookmarkReportRepository bookmarkReportRepository

    @Autowired
    private MemberReportRepository memberReportRepository

    @Autowired
    private MemberRepository memberRepository

    @Autowired
    private BookmarkRepository bookmarkRepository

    @Autowired
    private CategoryRepository categoryRepository

    private memberFactory = new MemberFactory()
    private bookmarkFactory = new BookmarkFactory()
    private categoryFactory = new CategoryFactory()

    @BeforeEach
    void setup() {
        memberRepository.deleteAll()
        memberReportRepository.deleteAll()
        bookmarkRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    def "북마크 신고"() {
        given:
        var reporter = memberRepository.save(memberFactory.testMember())
        var bookmarkOwner = memberRepository.save(memberFactory.testMember("bookmarkOwner", "bookmarker@pickly.com",
                "bookmarkOwner", "bookmarkOwner", "👍"))
        var category = categoryRepository.save(categoryFactory.testCategory(bookmarkOwner))
        var reported = bookmarkRepository.save(bookmarkFactory.testBookmark(bookmarkOwner, category))

        when:
        bookmarkReportService.reportBookmark(reporter.id, reported.id, "test")

        then:
        bookmarkReportRepository.count() == 1
    }

    def "자신의 북마크를 신고할 수 없다"() {
        given:
        var reporter = memberRepository.save(memberFactory.testMember())
        var category = categoryRepository.save(categoryFactory.testCategory(reporter))
        var reported = bookmarkRepository.save(bookmarkFactory.testBookmark(reporter, category))

        when:
        bookmarkReportService.reportBookmark(reporter.id, reported.id, "test")

        then:
        thrown(BusinessException)
    }

    def "중복된 신고를 할 수 없다"() {
        given:
        var reporter = memberRepository.save(memberFactory.testMember())
        var bookmarkOwner = memberRepository.save(memberFactory.testMember("bookmarkOwner", "bookmarker@pickly.com",
                "bookmarkOwner", "bookmarkOwner", "👍"))
        var category = categoryRepository.save(categoryFactory.testCategory(bookmarkOwner))
        var reported = bookmarkRepository.save(bookmarkFactory.testBookmark(bookmarkOwner, category))
        bookmarkReportService.reportBookmark(reporter.id, reported.id, "test")

        when:
        bookmarkReportService.reportBookmark(reporter.id, reported.id, "test")

        then:
        thrown(BusinessException)
    }
}
