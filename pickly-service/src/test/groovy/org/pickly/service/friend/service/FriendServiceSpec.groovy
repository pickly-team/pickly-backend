package org.pickly.service.friend.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.common.error.exception.BusinessException
import org.pickly.common.error.exception.ErrorCode
import org.pickly.service.friend.repository.interfaces.FriendRepository
import org.pickly.service.friend.service.dto.FriendNotificationStatusReqDTO
import org.pickly.service.friend.service.interfaces.FriendService
import org.pickly.service.member.entity.Member
import org.pickly.service.member.entity.Password
import org.pickly.service.member.repository.interfaces.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class FriendServiceSpec extends Specification {

    @Autowired
    private FriendService friendService

    @Autowired
    private FriendRepository friendRepository

    @Autowired
    private MemberRepository memberRepository


    @BeforeEach
    void setup() {
        memberRepository.deleteAll()
        friendRepository.deleteAll()
    }

    def "팔로우 중일 경우 > 알림 설정 ON"() {
        given:

        var follower = memberRepository.save(Member.builder()
                .email("front@pickly.com")
                .username("프론트엔드")
                .password(new Password("1234"))
                .name("프론트엔드")
                .nickname("프론트엔드")
                .profileEmoji("👍")
                .isHardMode(false)
                .build())

        var followee = memberRepository.save(Member.builder()
                .email("backend@pickly.com")
                .username("백엔드")
                .password(new Password("1234"))
                .name("백엔드")
                .nickname("백엔드")
                .profileEmoji("👍")
                .isHardMode(false)
                .build())

        friendService.follow(follower.id, followee.id)
        var REQUEST = new FriendNotificationStatusReqDTO(followee.id, true)

        when:
        friendService.setNotification(follower.id, REQUEST)

        then:
        def friend = friendRepository.findByFollowerIdAndFolloweeId(follower.getId(), followee.getId()).orElseThrow(
                () -> new BusinessException(ErrorCode.INVALID_INPUT_VALUE))
        friend.notificationEnabled == true
    }

    def "팔로우 중일 경우 > 알림 설정 OFF"() {
        given:

        var follower = memberRepository.save(Member.builder()
                .email("front@pickly.com")
                .username("프론트엔드")
                .password(new Password("1234"))
                .name("프론트엔드")
                .nickname("프론트엔드")
                .profileEmoji("👍")
                .isHardMode(false)
                .build())

        var followee = memberRepository.save(Member.builder()
                .email("backend@pickly.com")
                .username("백엔드")
                .password(new Password("1234"))
                .name("백엔드")
                .nickname("백엔드")
                .profileEmoji("👍")
                .isHardMode(false)
                .build())

        friendService.follow(follower.id, followee.id)
        var REQUEST = new FriendNotificationStatusReqDTO(followee.id, false)


        when:
        friendService.setNotification(follower.id, REQUEST)

        then:
        def friend = friendRepository.findByFollowerIdAndFolloweeId(follower.getId(), followee.getId()).orElseThrow(
                () -> new BusinessException(ErrorCode.INVALID_INPUT_VALUE))
        friend.notificationEnabled == false
    }

    def "팔로우 중이 아닐 때 > 알림을 끄려고 하면 실패한다."() {
        given:
        var follower = memberRepository.save(Member.builder()
                .email("front@pickly.com")
                .username("프론트엔드")
                .password(new Password("1234"))
                .name("프론트엔드")
                .nickname("프론트엔드")
                .profileEmoji("👍")
                .isHardMode(false)
                .build())

        var followee = memberRepository.save(Member.builder()
                .email("backend@pickly.com")
                .username("백엔드")
                .password(new Password("1234"))
                .name("백엔드")
                .nickname("백엔드")
                .profileEmoji("👍")
                .isHardMode(false)
                .build())

        var REQUEST = new FriendNotificationStatusReqDTO(followee.id, true)


        when:
        friendService.setNotification(follower.id, REQUEST)

        then:
        thrown BusinessException
    }
}
