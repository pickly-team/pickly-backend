package org.pickly.service.member.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.service.application.facade.MemberFacade
import org.pickly.service.bookmark.BookmarkFactory
import org.pickly.service.category.CategoryFactory
import org.pickly.service.domain.bookmark.repository.interfaces.BookmarkRepository
import org.pickly.service.domain.category.repository.interfaces.CategoryRepository
import org.pickly.service.domain.friend.service.FriendReadService
import org.pickly.service.domain.friend.service.FriendWriteService
import org.pickly.service.domain.member.repository.interfaces.MemberRepository
import org.pickly.service.domain.member.service.MemberReadService
import org.pickly.service.domain.member.service.MemberWriteService
import org.pickly.service.domain.member.dto.service.MemberProfileUpdateDTO
import org.pickly.service.member.MemberFactory
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
class MemberReadServiceSpec extends Specification {

    @Autowired
    private MemberFacade memberFacade

    @Autowired
    private MemberReadService memberReadService

    @Autowired
    private MemberWriteService memberWriteService

    @Autowired
    private MemberRepository memberRepository

    @Autowired
    private BookmarkRepository bookmarkRepository

    @Autowired
    private CategoryRepository categoryRepository

    @Autowired
    private FriendReadService friendReadService

    @Autowired
    private FriendWriteService friendWriteService

    private memberFactory = new MemberFactory()
    private bookmarkFactory = new BookmarkFactory()
    private categoryFactory = new CategoryFactory()

    @BeforeEach
    void setup() {
        memberRepository.deleteAll()
    }

    def "내 프로필 조회"() {
        given:
        var member = memberRepository.save(memberFactory.testMember())

        var category = categoryRepository.save(categoryFactory.testCategory(member))
        bookmarkRepository.save(bookmarkFactory.testBookmark(member, category))

        var member2 = memberRepository.save(memberFactory.testMember("picko2",
                "picko2@pickly.com", "picko2", "picko2", "👎"))

        friendWriteService.follow(member, member2)
        friendWriteService.follow(member2, member)

        when:
        def info = memberReadService.findById(member.id)

        then:
        info != null
        info.name == "picko"
        info.nickname == "iAmNotAPickyEater"
        info.profileEmoji == "👍"
    }

    def "사용자 프로필 조회"() {
        given:
        var member = memberRepository.save(memberFactory.testMember())

        when:
        def dto = memberFacade.findProfileById(member.id, member.id)

        then:
        dto != null
        dto.name == "picko"
        dto.nickname == "iAmNotAPickyEater"
        dto.profileEmoji == "👍"
        dto.isFollowing == false
    }


    def "사용자 프로필 수정_존재하지 않는 닉네임은 사용 가능"() {
        given:
        var member = memberRepository.save(memberFactory.testMember())

        when:
        memberWriteService.update(member, new MemberProfileUpdateDTO("수정", "수정", "👎"))

        then:
        def found = memberRepository.findById(member.id).orElse(null)

        found != null
        found.name == "수정"
        found.nickname == "수정"
        found.profileEmoji == "👎"
    }

    def "사용자 탈퇴"() {
        given:
        var member = memberRepository.save(memberFactory.testMember())

        when:
        memberWriteService.delete(member)

        then:
        var found = memberRepository.findById(member.id).orElse(null)

        found == null
    }

    def "사용자 알림 설정"() {
        given:
        var fcmToken = "FCM_TOKEN"
        var timezone = "Asia/Seoul"
        var member = memberRepository.save(memberFactory.testMember())

        when:
        memberWriteService.updateNotificationSetting(member, timezone, fcmToken);

        then:
        var found = memberRepository.findById(member.id).orElse(null)

        found != null
        member.fcmToken == fcmToken
        member.timezone == timezone
    }
}
