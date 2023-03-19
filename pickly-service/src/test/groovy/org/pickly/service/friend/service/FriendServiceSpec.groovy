package org.pickly.service.friend.service

import org.junit.jupiter.api.BeforeEach
import org.pickly.common.error.exception.BusinessException
import org.pickly.service.friend.FriendFactory
import org.pickly.service.friend.repository.interfaces.FriendRepository
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
    private FriendFactory friendFactory = new FriendFactory()

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
                .build());

        var followee = memberRepository.save(Member.builder()
                .email("backend@pickly.com")
                .username("백엔드")
                .password(new Password("1234"))
                .name("백엔드")
                .nickname("백엔드")
                .profileEmoji("👍")
                .isHardMode(false)
                .build());

        friendService.follow(follower.getId(), followee.getId())
        var friend = friendFactory.testFriend(follower, followee)

        when:
        friendService.enableNotification(friend.follower.id, friend.followee.id)

        then:
        def testFriend = friendRepository.findByFollowerIdAndFolloweeId(follower.getId(), followee.getId())
        testFriend.notificationEnabled == true
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
                .build());

        var followee = memberRepository.save(Member.builder()
                .email("backend@pickly.com")
                .username("백엔드")
                .password(new Password("1234"))
                .name("백엔드")
                .nickname("백엔드")
                .profileEmoji("👍")
                .isHardMode(false)
                .build());

        friendService.follow(follower.getId(), followee.getId())
        var friend = friendFactory.testFriend(follower, followee)

        when:
        friendService.disableNotification(friend.follower.id, friend.followee.id)

        then:
        def testFriend = friendRepository.findByFollowerIdAndFolloweeId(follower.getId(), followee.getId())
        testFriend.notificationEnabled == false
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
                .build());

        var followee = memberRepository.save(Member.builder()
                .email("backend@pickly.com")
                .username("백엔드")
                .password(new Password("1234"))
                .name("백엔드")
                .nickname("백엔드")
                .profileEmoji("👍")
                .isHardMode(false)
                .build());

        var friend = friendFactory.testFriend(follower, followee)

        when:
        friendService.disableNotification(friend.follower.id, friend.followee.id)

        then:
        thrown BusinessException
    }
}