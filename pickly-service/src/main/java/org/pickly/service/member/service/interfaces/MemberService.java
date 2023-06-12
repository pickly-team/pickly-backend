package org.pickly.service.member.service.interfaces;

import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.dto.*;

import java.util.List;
import java.util.Map;

public interface MemberService {

  void existsById(Long memberId);

  void updateMyProfile(Long memberId, MemberProfileUpdateDTO request);

  Member findById(Long id);

  MyProfileDTO findMyProfile(Long memberId);

  MemberModeDTO findModeByMemberId(Long memberId);

  MemberProfileDTO findProfileById(Long loginId, Long memberId);

  void deleteMember(Long memberId);

  HardModeDTO setHardMode(Long memberId, MemberStatusDTO request);

  MemberRegisterDto register(String token);

  Map<Long, String> findTokenByIds(List<Long> memberIds);

}
