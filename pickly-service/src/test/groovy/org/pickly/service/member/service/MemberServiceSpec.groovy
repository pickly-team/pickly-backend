package org.pickly.service.member.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.service.bookmark.BookmarkFactory
import org.pickly.service.domain.bookmark.repository.interfaces.BookmarkRepository
import org.pickly.service.category.CategoryFactory
import org.pickly.service.domain.category.repository.interfaces.CategoryRepository

import org.pickly.service.member.MemberFactory
import org.pickly.service.domain.member.exception.MemberException
import org.pickly.service.domain.member.repository.interfaces.MemberRepository
import org.pickly.service.domain.member.service.dto.MemberProfileUpdateDTO
import org.pickly.service.domain.member.service.interfaces.MemberService
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
    private FriendReadService friendService

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


    def "사용자 프로필 수정_존재하지 않는 닉네임은 사용 가능"() {
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

    def "사용자 프로필 수정_이미 존재하는 닉네임은 사용 불가능"() {
        given:
        var member = memberRepository.save(memberFactory.testMember("picko", "test@gmail.com"))
        var member2 = memberRepository.save(memberFactory.testMember("picko2", "test2@gmail.com"))

        when:
        memberService.updateMyProfile(member.id, new MemberProfileUpdateDTO("수정", member2.getNickname(), "👎"))

        then:
        thrown(MemberException.NicknameDuplicateException)
    }

    def "사용자 탈퇴"() {
        given:
        var member = memberRepository.save(memberFactory.testMember())

        when:
        memberService.deleteMember(member.id)

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
        memberService.updateNotificationSettings(member.id, timezone, fcmToken);

        then:
        var found = memberRepository.findById(member.id).orElse(null)

        found != null
        member.fcmToken == fcmToken
        member.timezone == timezone
    }
}
