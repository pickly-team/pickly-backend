package org.pickly.service.member.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.common.error.exception.InvalidValueException
import org.pickly.service.member.MemberFactory
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

    private final MemberFactory memberFactory = new MemberFactory()

    @Autowired
    private MemberService memberService

    @Autowired
    private MemberRepository memberRepository

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

        when:
        def dto = memberService.findProfileByMemberId(member.id)

        then:
        dto != null
        dto.name == "테스트"
        dto.nickname == "테스트"
        dto.profileEmoji == "👍"
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

    def "비밀번호 변경 - 성공"() {
        given:
        var member = memberFactory.testMember()
        memberRepository.save(member)

        when:
        memberService.changePassword(member.id, "nobodyKnows123", "test")

        then:
        noExceptionThrown()
    }

    def "비밀번호 변경 - 기존 비밀번호를 틀린 경우 예외 발생"() {
        given:
        var member = memberFactory.testMember()
        memberRepository.save(member)

        when:
        memberService.changePassword(member.id, "test", "test")

        then:
        thrown InvalidValueException
    }

    def "비밀번호 변경 - 기존 비밀번호가 새로운 비밀번호와 같은 경우 예외 발생"() {
        given:
        var member = memberFactory.testMember()
        memberRepository.save(member)

        when:
        memberService.changePassword(member.id, "nobodyKnows123", "nobodyKnows123")

        then:
        thrown InvalidValueException
    }
}
