package org.pickly.service.member.common;

import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.response.MemberModeRes;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.controller.response.MyProfileRes;
import org.pickly.service.member.entity.Member;
import org.pickly.service.notification.entity.NotificationStandard;
import org.pickly.service.member.service.dto.MemberModeDTO;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MyProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

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

  public MemberInfoRes toMemberInfoRes(MemberInfoDTO dto) {
    return MemberInfoRes.builder()
        .id(dto.getId())
        .nickname(dto.getNickname())
        .profileEmoji(dto.getProfileEmoji())
        .isFollowing(dto.getIsFollowing())
        .build();
  }

  public MemberStatusRes toMemberStatusRes(MemberModeDTO statusDTO) {
    return new MemberStatusRes(statusDTO.getUserMode());
  }
  
  public MemberModeRes toResponse(MemberModeDTO dto) {
    return new MemberModeRes(dto.getIsHardMode(), dto.getStandardDate());
  }

  public MemberProfileUpdateDTO toDTO(MemberProfileUpdateReq request) {
    return new MemberProfileUpdateDTO(
        request.getName(), request.getNickname(), request.getProfileEmoji()
    );
  }

  public MemberStatusDTO toStatusDTO(MemberStatusReq request) {
    return new MemberStatusDTO(request.getIsHardMode());
  }

  public MyProfileDTO toMyProfileDTO(Member member, Long followersCount, Long followeesCount,
      Long bookmarksCount) {
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

  public MemberProfileRes toResponse(MemberProfileDTO dto) {
    return MemberProfileRes.builder()
        .id(dto.getId())
        .name(dto.getName())
        .nickname(dto.getNickname())
        .profileEmoji(dto.getProfileEmoji())
        .isFollowing(dto.getIsFollowing())
        .build();
  }

  public MemberProfileDTO toMemberProfileDTO(Member member, boolean isFollowing) {
    return MemberProfileDTO.builder()
        .id(member.getId())
        .name(member.getName())
        .nickname(member.getNickname())
        .profileEmoji(member.getProfileEmoji())
        .isFollowing(isFollowing)
        .build();
  }

  public MemberInfoDTO toMemberInfoDTO(Member member, Boolean isFollowing) {
    return MemberInfoDTO.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .profileEmoji(member.getProfileEmoji())
        .isFollowing(isFollowing)
        .build();
  }

  public MemberModeDTO toMemberStatusDTO(MemberMode isHardMode) {
    return new MemberModeDTO(isHardMode.getDescription());
  }
  
  public MemberModeDTO toMemberModeDTO(Member member, NotificationStandard standard) {
    return MemberModeDTO.builder()
        .isHardMode(member.getIsHardMode())
        .standardDate(standard.getStandardDate())
        .build();
  }

}
