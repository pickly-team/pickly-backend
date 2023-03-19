package org.pickly.service.member.common;

import com.google.firebase.auth.FirebaseToken;
import org.pickly.service.member.controller.request.MemberProfileUpdateReq;
import org.pickly.service.member.controller.response.MemberInfoRes;
import org.pickly.service.member.controller.response.MemberProfileRes;
import org.pickly.service.member.controller.response.MemberRegisterRes;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.entity.Password;
import org.pickly.service.member.service.dto.MemberInfoDTO;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MemberRegisterDto;
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

  public MemberInfoDTO toMemberInfoDTO(Member member, Boolean isFollowing) {
    return MemberInfoDTO.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .profileEmoji(member.getProfileEmoji())
        .isFollowing(isFollowing)
        .build();
  }


  public MemberRegisterDto toMemberRegisterDTO(Member member) {
    return new MemberRegisterDto(
        member.getUsername(),
        member.getIsHardMode(),
        member.getEmail(),
        member.getName(),
        member.getNickname(),
        member.getPassword()
    );
  }

  public Member tokenToMember(FirebaseToken token) {
    //TODO: password nullable한 값으로 변경?
    Password password = new Password("test123");
  
    return Member.builder()
        .username(token.getUid())
        .email(token.getEmail())
        .name(token.getName())
        .nickname("")
        .isHardMode(false)
        .password(password)
        .build();
  }

  public MemberRegisterRes toMemberRegisterResponse(MemberRegisterDto dto) {
    return new MemberRegisterRes(dto.getUsername(), dto.getIsHardMode(), dto.getEmail(),
        dto.getName(), dto.getNickname());
  }
}