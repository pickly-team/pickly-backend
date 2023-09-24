package org.pickly.service.notification.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.service.domain.member.repository.interfaces.MemberRepository
import org.pickly.service.domain.notification.repository.interfaces.NotificationStandardRepository
import org.pickly.service.domain.notification.service.standard.NotificationStandardReadService
import org.pickly.service.domain.notification.service.standard.NotificationStandardWriteService
import org.pickly.service.member.MemberFactory
import org.pickly.service.notification.NotificationStandardFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers
import spock.lang.Specification

import java.time.LocalTime

@Transactional
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class NotificationStandardServiceSpec extends Specification {

    @Autowired
    private NotificationStandardWriteService notificationStandardWriteService

    @Autowired
    private NotificationStandardReadService notificationStandardReadService

    @Autowired
    private NotificationStandardRepository notificationStandardRepository

    @Autowired
    private MemberRepository memberRepository

    private NotificationStandardFactory notificationStandardFactory = new NotificationStandardFactory()
    private MemberFactory memberFactory = new MemberFactory()

    @BeforeEach
    void setup() {
        notificationStandardRepository.deleteAll()
        memberRepository.deleteAll()
    }

    def "알림 설정이 없는 경우 생성할 수 있다"() {
        given:
        var member = memberRepository.save(memberFactory.testMember())

        when:
        notificationStandardWriteService.create(member)

        then:
        var found = notificationStandardReadService.findByMemberId(member.id)
        found.isActive == true
        found.notifyDailyAt == LocalTime.of(9, 0)
    }

    def "사용자가 알림 설정을 이미 가지고 있는 경우 수정할 수 있다"() {
        given:
        var member = memberRepository.save(memberFactory.testMember())
        var standard = notificationStandardRepository.save(notificationStandardFactory.testNotificationStandard(member))

        when:
        notificationStandardWriteService.update(standard, false, LocalTime.of(10, 0))

        then:
        var found = notificationStandardReadService.findByMemberId(member.id)
        found.isActive == false
        found.notifyDailyAt == LocalTime.of(10, 0)
    }

}
