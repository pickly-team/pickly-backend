package org.pickly.service.member.service.interfaces;

import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MemberRegisterDto;

public interface MemberService{

  void updateMyProfile(Long memberId, MemberProfileUpdateDTO request);

  MemberProfileDTO findProfileByNickname(String nickname);

  void register(MemberRegisterDto request);
}
