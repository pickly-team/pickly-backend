package org.pickly.service.member.service.interfaces;

import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;

public interface MemberService {

  void updateMyProfile(Long memberId, MemberProfileUpdateDTO request);

  MemberProfileDTO findProfileByNickname(String nickname);
}
