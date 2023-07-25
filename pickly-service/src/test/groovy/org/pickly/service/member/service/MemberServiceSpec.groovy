package org.pickly.service.member.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.service.bookmark.BookmarkFactory
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository
import org.pickly.service.category.CategoryFactory
import org.pickly.service.category.repository.interfaces.CategoryRepository
import org.pickly.service.friend.service.interfaces.FriendService
import org.pickly.service.member.MemberFactory
import org.pickly.service.member.repository.interfaces.MemberRepository
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO
import org.pickly.service.member.service.interfaces.MemberService
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
class MemberServiceSpec extends Specification {

    @Autowired
    private MemberService memberService

    @Autowired
    private MemberRepository memberRepository

    @Autowired
    private BookmarkRepository bookmarkRepository

    @Autowired
    private CategoryRepository categoryRepository

    @Autowired
    private FriendService friendService

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

        friendService.follow(member.id, member2.id)
        friendService.follow(member2.id, member.id)

        when:
        def dto = memberService.findMyProfile(member.id)

        then:
        dto != null
        dto.name == "picko"
        dto.nickname == "iAmNotAPickyEater"
        dto.profileEmoji == "👍"
        dto.bookmarksCount == 1
        dto.followersCount == 1
        dto.followeesCount == 1
    }

    def "사용자 프로필 조회"() {
        given:
        var member = memberRepository.save(memberFactory.testMember())

        when:
        def dto = memberService.findProfileById(member.id, member.id)

        then:
        dto != null
        dto.name == "picko"
        dto.nickname == "iAmNotAPickyEater"
        dto.profileEmoji == "👍"
        dto.isFollowing == false
    }


    def "사용자 프로필 수정"() {
        given:
        var member = memberRepository.save(memberFactory.testMember())

        when:
        memberService.updateMyProfile(member.id, new MemberProfileUpdateDTO("수정", "수정", "👎"))

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
        memberService.deleteMember(member.id)

        then:
        var found = memberRepository.findById(member.id).orElse(null)

        found != null
    }

    def "사용자 알림 설정"() {
        given:
        var fcmToken = "FCM_TOKEN"
        var timezone = "Asia/Seoul"
        var member = memberRepository.save(memberFactory.testMember())

        when:
        memberService.updateNotificationSettings(member.id, timezone, fcmToken);

        then:
        var found = memberRepository.findById(member.id).orElse(null)

        found != null
        member.fcmToken == fcmToken
        member.timezone == timezone
    }
}
