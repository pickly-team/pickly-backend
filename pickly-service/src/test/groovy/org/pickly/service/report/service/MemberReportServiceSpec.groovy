package org.pickly.service.report.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.service.common.error.exception.BusinessException
import org.pickly.service.domain.report.service.MemberReportService
import org.pickly.service.member.MemberFactory
import org.pickly.service.domain.member.repository.interfaces.MemberRepository
import org.pickly.service.domain.report.repository.MemberReportRepository
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
class MemberReportServiceSpec extends Specification {

    @Autowired
    private MemberReportService memberReportService

    @Autowired
    private MemberReportRepository memberReportRepository

    @Autowired
    private MemberRepository memberRepository

    private memberFactory = new MemberFactory()

    @BeforeEach
    void setup() {
        memberRepository.deleteAll()
        memberReportRepository.deleteAll()
    }

    def "사용자 신고"() {
        given:
        var reporter = memberRepository.save(memberFactory.testMember())
        var reported = memberRepository.save(memberFactory.testMember("reported", "reported@gmail.com", "reported",
                "reported", "👍"))

        when:
        memberReportService.reportMember(reporter.id, reported.id, "test")

        then:
        memberReportRepository.count() == 1
    }

    def "자기 자신을 신고할 수 없다"() {
        given:
        var reporter = memberRepository.save(memberFactory.testMember())

        when:
        memberReportService.reportMember(reporter.id, reporter.id, "test")

        then:
        thrown(BusinessException)
    }

    def "이미 신고한 사용자를 신고할 수 없다"() {
        given:
        var reporter = memberRepository.save(memberFactory.testMember())
        var reported = memberRepository.save(memberFactory.testMember("reported", "reported@gmail.com", "reported",
                "reported", "👍"))
        memberReportService.reportMember(reporter.id, reported.id, "test")

        when:
        memberReportService.reportMember(reporter.id, reported.id, "test")

        then:
        thrown(BusinessException)
    }
}
