package org.pickly.service.member.common;

import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.response.MemberInfoRes;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.dto.MemberInfoDTO;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

  public MemberProfileRes toResponse(MemberProfileDTO dto) {
    return new MemberProfileRes(dto.getName(), dto.getNickname(), dto.getProfileEmoji());
  }

  public MemberInfoRes toMemberInfoRes(MemberInfoDTO dto) {
    return MemberInfoRes.builder()
        .id(dto.getId())
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

  public MemberProfileDTO toMemberProfileDTO(Member member) {
    return new MemberProfileDTO(member.getName(), member.getNickname(), member.getProfileEmoji());
  }

  public MemberInfoDTO toMemberInfoDTO(Member member, Boolean isFollowing) {
    return MemberInfoDTO.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .profileEmoji(member.getProfileEmoji())
        .isFollowing(isFollowing)
        .build();
  }

}
