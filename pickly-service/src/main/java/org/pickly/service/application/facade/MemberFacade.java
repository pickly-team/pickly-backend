package org.pickly.service.application.facade;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.block.service.BlockReadService;
import org.pickly.service.domain.bookmark.service.BookmarkReadService;
import org.pickly.service.domain.friend.service.FriendReadService;
import org.pickly.service.domain.member.common.MemberMapper;
import org.pickly.service.domain.member.dto.service.*;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.entity.MemberStatus;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.member.service.MemberWriteService;
import org.pickly.service.domain.notification.service.standard.NotificationStandardReadService;
import org.pickly.service.domain.notification.service.standard.NotificationStandardWriteService;
import org.pickly.service.domain.notification.service.NotificationWriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberFacade {

  private final MemberMapper memberMapper;
  private final MemberReadService memberReadService;
  private final MemberWriteService memberWriteService;
  private final BookmarkReadService bookmarkReadService;
  private final FriendReadService friendReadService;
  private final BlockReadService blockReadService;
  private final NotificationWriteService notificationWriteService;
  private final NotificationStandardReadService notificationStandardReadService;
  private final NotificationStandardWriteService notificationStandardWriteService;

  @Transactional
  public Member join(Member request) {
    var email = request.getEmail();
    if (memberReadService.existsByEmail(email)) {
      return login(email);
    } else {
      return create(request);
    }
  }

  @Transactional
  public Member create(Member request) {
    Member member = memberWriteService.create(request);
    notificationStandardWriteService.create(member);
    return member;
  }

  private Member login(final String email) {
    Member savedMember = memberReadService.findByEmail(email);
    MemberStatus status = savedMember.getStatus();
    status.login();
    return savedMember;
  }

  @Transactional
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
    return memberMapper.toResponse(member, isFollowing, isBlocked);
  }

}
