package org.pickly.service.notification.service

import org.pickly.service.bookmark.BookmarkFactory
import org.pickly.service.category.CategoryFactory
import org.pickly.service.domain.bookmark.repository.interfaces.BookmarkRepository
import org.pickly.service.domain.category.repository.interfaces.CategoryRepository
import org.pickly.service.domain.member.repository.interfaces.MemberRepository
import org.pickly.service.domain.notification.repository.interfaces.NotificationRepository
import org.pickly.service.domain.notification.service.NotificationWriteService
import org.pickly.service.member.MemberFactory
import org.pickly.service.notification.NotificationFactory
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
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationServiceSpec extends Specification {

    @Autowired
    private NotificationWriteService notificationWriteService

    @Autowired
    private BookmarkRepository bookmarkRepository

    @Autowired
    private CategoryRepository categoryRepository

    @Autowired
    private MemberRepository memberRepository

    @Autowired
    private NotificationRepository notificationRepository

    private BookmarkFactory bookmarkFactory = new BookmarkFactory()
    private CategoryFactory categoryFactory = new CategoryFactory()
    private MemberFactory memberFactory = new MemberFactory()
    private NotificationFactory notificationFactory = new NotificationFactory()

    def "특정 알림을 읽음 처리한다"() {
        given:
        var member = memberFactory.testMember()
        memberRepository.save(member)
        var category = categoryFactory.testCategory(member)
        categoryRepository.save(category)
        var bookmark = bookmarkFactory.testBookmark(member, category)
        bookmarkRepository.save(bookmark)
        var notification = notificationFactory.testNotification(member.id, bookmark.id)
        notificationRepository.save(notification)

        when:
        notificationWriteService.read(notification)

        then:
        notification.checked
    }

}