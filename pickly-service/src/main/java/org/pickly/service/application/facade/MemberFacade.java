package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.block.service.BlockReadService;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.friend.service.FriendReadService;
import org.pickly.service.domain.member.common.MemberMapper;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.member.service.MemberWriteService;
import org.pickly.service.domain.member.service.dto.*;
import org.pickly.service.domain.notification.service.NotificationStandardReadService;
import org.pickly.service.domain.notification.service.NotificationStandardWriteService;
import org.pickly.service.domain.notification.service.NotificationWriteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFacade {

  // FIXME: mapper가 service 계층까지 들어오는게 맞는가?
  private final MemberMapper memberMapper;
  private final MemberReadService memberReadService;
  private final MemberWriteService memberWriteService;
  private final BookmarkReadService bookmarkReadService;
  private final FriendReadService friendReadService;
  private final BlockReadService blockReadService;
  private final NotificationWriteService notificationWriteService;
  private final NotificationStandardReadService notificationStandardReadService;
  private final NotificationStandardWriteService notificationStandardWriteService;

  public Member create(Member member) {
    var email = member.getEmail();
    if (memberReadService.existsByEmail(email)) {
      return memberReadService.findByEmail(email);
    } else {
      Member savedMember = memberWriteService.create(member);
      notificationStandardWriteService.create(savedMember);
      return savedMember;
    }
  }

  public void delete(Long memberId) {
    var member = memberReadService.findById(memberId);
    memberWriteService.delete(member);

    notificationWriteService.deleteByMember(memberId);
  }

  public Member setHardMode(Long memberId, MemberStatusDTO request) {
    var member = memberReadService.findById(memberId);
    return memberWriteService.setHardMode(member, request.getIsHardMode());
  }

  public void updateNotificationSettings(Long memberId, String timezone, String fcmToken) {
    var member = memberReadService.findById(memberId);
    memberWriteService.updateNotificationSetting(member, timezone, fcmToken);
  }

  public void update(Long memberId, MemberProfileUpdateDTO request) {
    var member = memberReadService.findById(memberId);
    memberReadService.isValidNickname(member, request.getNickname());

    memberWriteService.update(member, request);
  }

  public String makeAuthenticationCode(Long memberId) {
    memberReadService.existsById(memberId);
    return memberWriteService.makeAuthenticationCode(memberId);
  }

  public MemberModeDTO findModeByMemberId(Long memberId) {
    var member = memberReadService.findById(memberId);
    var standard = notificationStandardReadService.findByMemberId(memberId);
    return memberMapper.toMemberModeDTO(member, standard);
  }

  public MyProfileDTO findMyProfile(final Long memberId) {
    var member = memberReadService.findById(memberId);
    Long followersCount = friendReadService.getFollowerCount(memberId);
    Long followeesCount = friendReadService.getFolloweeCount(memberId);
    Long bookmarksCount = bookmarkReadService.countByMemberId(memberId);
    return memberMapper.toMyProfileDTO(member, followersCount, followeesCount, bookmarksCount);
  }

  public MemberProfileDTO findProfileById(final Long loginId, final Long memberId) {
    boolean isFollowing = friendReadService.existsByFollowerIdAndFolloweeId(loginId, memberId);
    boolean isBlocked = blockReadService.existsByBlockerIdAndBlockeeId(loginId, memberId);
    Member member = memberReadService.findById(memberId);
    return memberMapper.toMemberProfileDTO(member, isFollowing, isBlocked);
  }

}
