package org.pickly.service.member.common;

import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.controller.response.MemberRegisterRes;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MemberRegisterDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

  public MemberProfileRes toResponse(MemberProfileDTO dto) {
    return new MemberProfileRes(dto.getName(), dto.getNickname(), dto.getProfileEmoji());
  }

  public MemberProfileUpdateDTO toDTO(MemberProfileUpdateReq request) {
    return new MemberProfileUpdateDTO(
        request.getName(), request.getNickname(), request.getProfileEmoji()
    );
  }

  public MemberProfileDTO toMemberProfileDTO(Member member) {
    return new MemberProfileDTO(member.getName(), member.getNickname(), member.getProfileEmoji());
  }

  public MemberRegisterDto toMemberRegisterDTO(String username, Boolean isHardMode, String email, String name, String nickname){
    return new MemberRegisterDto(
        username,
        isHardMode,
        email,
        name,
        nickname
    );
  }

  public MemberRegisterRes toMemberRegisterResponse(MemberRegisterDto dto){
    return new MemberRegisterRes(dto.getUsername(), dto.getIsHardMode(), dto.getEmail(), dto.getName(), dto.getNickname());
  }
}
