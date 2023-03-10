package org.pickly.service.member.service.interfaces;

import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MemberStatusDTO;

public interface MemberService {

  void existsById(Long memberId);

  void updateMyProfile(Long memberId, MemberProfileUpdateDTO request);

  Member findById(Long id);

  MemberProfileDTO findProfileByMemberId(Long memberId, Long loginId);

  MemberStatusDTO switchToHardMode(Long memberId);
}
