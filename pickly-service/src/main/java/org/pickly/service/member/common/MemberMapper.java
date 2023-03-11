package org.pickly.service.member.common;

import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

  public MemberProfileRes toResponse(MemberProfileDTO dto) {
    return MemberProfileRes.builder()
        .id(dto.getId())
        .name(dto.getName())
        .nickname(dto.getNickname())
        .profileEmoji(dto.getProfileEmoji())
        .isFollowing(dto.getIsFollowing())
        .build();
  }

  public MemberProfileUpdateDTO toDTO(MemberProfileUpdateReq request) {
    return new MemberProfileUpdateDTO(
        request.getName(), request.getNickname(), request.getProfileEmoji()
    );
  }

  public MemberProfileDTO toMemberProfileDTO(Member member, Boolean isFollowing) {
    return MemberProfileDTO.builder()
        .id(member.getId())
        .name(member.getName())
        .nickname(member.getNickname())
        .profileEmoji(member.getProfileEmoji())
        .isFollowing(isFollowing)
        .build();
  }

}
