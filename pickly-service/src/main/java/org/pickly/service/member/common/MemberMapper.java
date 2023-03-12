package org.pickly.service.member.common;

import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.request.MemberStatusReq;
import org.pickly.service.member.controller.response.MemberInfoRes;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.controller.response.MemberStatusRes;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.entity.MemberMode;
import org.pickly.service.member.service.dto.MemberInfoDTO;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MemberStatusRequestDTO;
import org.pickly.service.member.service.dto.MemberStatusResponseDTO;
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

  public MemberInfoRes toMemberInfoRes(MemberInfoDTO dto) {
    return MemberInfoRes.builder()
        .id(dto.getId())
        .nickname(dto.getNickname())
        .profileEmoji(dto.getProfileEmoji())
        .isFollowing(dto.getIsFollowing())
        .build();
  }

  public MemberStatusRes toMemberStatusRes(MemberStatusResponseDTO statusDTO) {
    return new MemberStatusRes(statusDTO.getUserMode());
  }

  public MemberProfileUpdateDTO toDTO(MemberProfileUpdateReq request) {
    return new MemberProfileUpdateDTO(
        request.getName(), request.getNickname(), request.getProfileEmoji()
    );
  }

  public MemberStatusRequestDTO toStatusDTO(MemberStatusReq request) {
    return new MemberStatusRequestDTO(request.getIsHardMode());
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

  public MemberInfoDTO toMemberInfoDTO(Member member, Boolean isFollowing) {
    return MemberInfoDTO.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .profileEmoji(member.getProfileEmoji())
        .isFollowing(isFollowing)
        .build();
  }

  public MemberStatusResponseDTO toMemberStatusDTO(MemberMode isHardMode) {
    return new MemberStatusResponseDTO(isHardMode.getDescription());
  }

}
