package org.pickly.service.domain.member.common;

import com.google.firebase.auth.FirebaseToken;
import org.pickly.service.domain.member.dto.controller.request.MemberProfileUpdateReq;
import org.pickly.service.domain.member.dto.controller.request.MemberStatusReq;
import org.pickly.service.domain.member.dto.controller.response.*;
import org.pickly.service.domain.member.dto.service.*;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.entity.MemberStatus;
import org.pickly.service.domain.member.entity.Password;
import org.pickly.service.domain.notification.entity.NotificationStandard;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

  public Member tokenToMember(FirebaseToken token) {
    //TODO: password nullable한 값으로 변경?
    Password password = new Password("test123");
    MemberStatus status = new MemberStatus();

    return Member.builder()
        .username(token.getUid())
        .email(token.getEmail())
        .name(token.getName())
        .nickname(token.getUid())
        .isHardMode(false)
        .password(password)
        .status(status)
        .build();
  }

  public MemberRegisterRes toResponse(Member member) {
    return new MemberRegisterRes(
        member.getUsername(), member.getIsHardMode(), member.getEmail(),
        member.getName(), member.getNickname(), member.getId()
    );
  }

  public MyProfileRes toResponse(MyProfileDTO dto) {
    return MyProfileRes.builder()
        .id(dto.getId())
        .name(dto.getName())
        .nickname(dto.getNickname())
        .profileEmoji(dto.getProfileEmoji())
        .followersCount(dto.getFollowersCount())
        .followeesCount(dto.getFolloweesCount())
        .bookmarksCount(dto.getBookmarksCount())
        .build();
  }

  public MemberProfileRes toResponse(MemberProfileDTO dto) {
    return MemberProfileRes.builder()
        .id(dto.getId())
        .name(dto.getName())
        .nickname(dto.getNickname())
        .profileEmoji(dto.getProfileEmoji())
        .isFollowing(dto.getIsFollowing())
        .isBlocked(dto.getIsBlocked())
        .build();
  }

  public MyProfileDTO toMyProfileDTO(
      Member member, Long followersCount, Long followeesCount, Long bookmarksCount
  ) {

    member.handleNullName();
    member.handleNullNickname();

    return MyProfileDTO.builder()
        .id(member.getId())
        .name(member.getName())
        .nickname(member.getNickname())
        .profileEmoji(member.getProfileEmoji())
        .followersCount(followersCount)
        .followeesCount(followeesCount)
        .bookmarksCount(bookmarksCount)
        .build();
  }

  public MemberProfileDTO toResponse(Member member, boolean isFollowing, boolean isBlocked) {
    return MemberProfileDTO.builder()
        .id(member.getId())
        .name(member.getName())
        .nickname(member.getNickname())
        .profileEmoji(member.getProfileEmoji())
        .isFollowing(isFollowing)
        .isBlocked(isBlocked)
        .build();
  }

  public HardModeRes toMemberStatusRes(Member member) {
    return new HardModeRes(member.isHardMode(member.getIsHardMode()));
  }

  public MemberStatusDTO toStatusDTO(MemberStatusReq request) {
    return new MemberStatusDTO(request.getIsHardMode());
  }

  public MemberModeRes toResponse(MemberModeDTO dto) {
    return new MemberModeRes(dto.getIsHardMode(), dto.getNotifyDailyAt());
  }

  public MemberProfileUpdateDTO toDTO(MemberProfileUpdateReq request) {
    return new MemberProfileUpdateDTO(
        request.getName(), request.getNickname(), request.getProfileEmoji()
    );
  }

  public MemberModeDTO toMemberModeDTO(Member member, NotificationStandard standard) {
    return MemberModeDTO.builder()
        .isHardMode(member.getIsHardMode())
        .notifyDailyAt(standard.getNotifyDailyAt())
        .build();
  }

  public SearchMemberResultRes toSearchMemberResultRes(SearchMemberResultResDTO dto) {
    return new SearchMemberResultRes(
        dto.getMemberId(),
        dto.getNickname(),
        dto.getIsFollowing(),
        dto.getEmoji(),
        dto.getIsBlocked());
  }

}
