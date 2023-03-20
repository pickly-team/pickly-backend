package org.pickly.service.member.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.service.bookmark.BookmarkFactory
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository
import org.pickly.service.category.CategoryFactory
import org.pickly.service.category.repository.interfaces.CategoryRepository
import org.pickly.service.friend.repository.interfaces.FriendRepository
import org.pickly.service.friend.service.interfaces.FriendService
import org.pickly.service.member.entity.Member
import org.pickly.service.member.entity.Password
import org.pickly.service.member.repository.interfaces.MemberRepository
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO
import org.pickly.service.member.service.interfaces.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
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

    private bookmarkFactory = new BookmarkFactory()
    private categoryFactory = new CategoryFactory()

    @BeforeEach
    void setup() {
        memberRepository.deleteAll()
    }

    def "사용자 프로필 조회"() {
        given:
        var member = memberRepository.save(Member.builder()
                .email("test@pickly.com")
                .username("test")
                .password(new Password("test"))
                .name("테스트")
                .nickname("테스트")
                .profileEmoji("👍")
                .isHardMode(false)
                .build())

        var category = categoryRepository.save(categoryFactory.testCategory(member))
        bookmarkRepository.save(bookmarkFactory.testBookmark(member, category))

        var member2 = memberRepository.save(Member.builder()
                .email("test2@pickly.com")
                .username("test2")
                .password(new Password("test"))
                .name("테스트2")
                .nickname("테스트2")
                .profileEmoji("👍")
                .isHardMode(false)
                .build())

        friendService.follow(member.id, member2.id)
        friendService.follow(member2.id, member.id)

        when:
        def dto = memberService.findProfileByMemberId(member.id, member.id)

        then:
        dto != null
        dto.name == "테스트"
        dto.nickname == "테스트"
        dto.profileEmoji == "👍"
        dto.bookmarksCount == 1
        dto.followersCount == 1
        dto.followeesCount == 1
    }


    def "사용자 프로필 수정"() {
        given:
        var member = memberRepository.save(Member.builder()
                .email("test@pickly.com")
                .username("test")
                .password(new Password("test"))
                .name("테스트")
                .nickname("테스트")
                .profileEmoji("👍")
                .isHardMode(false)
                .build())

        when:
        memberService.updateMyProfile(member.id, new MemberProfileUpdateDTO("수정", "수정", "👎"))

        then:
        def found = memberRepository.findById(member.id).orElse(null)

        found != null
        found.name == "수정"
        found.nickname == "수정"
        found.profileEmoji == "👎"
    }
}
