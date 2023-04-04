package org.pickly.service.member.service.interfaces;

import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.dto.HardModeDTO;
import org.pickly.service.member.service.dto.MemberModeDTO;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MemberStatusDTO;
import org.pickly.service.member.service.dto.MemberModeDTO;
import org.pickly.service.member.service.dto.MyProfileDTO;

public interface MemberService {

  void existsById(Long memberId);

  void updateMyProfile(Long memberId, MemberProfileUpdateDTO request);

  Member findById(Long id);

  MyProfileDTO findMyProfile(Long memberId);

  MemberModeDTO findModeByMemberId(Long memberId);

  MemberProfileDTO findProfileById(Long loginId, Long memberId);

  void deleteMember(Long memberId);

  HardModeDTO setHardMode(Long memberId, MemberStatusDTO request);
}
