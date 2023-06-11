package org.pickly.service.member.service.interfaces;

import java.util.List;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.service.dto.HardModeDTO;
import org.pickly.service.member.service.dto.MemberModeDTO;
import org.pickly.service.member.service.dto.MemberProfileDTO;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.dto.MemberRegisterDto;
import org.pickly.service.member.service.dto.MemberStatusDTO;
import org.pickly.service.member.service.dto.MyProfileDTO;
import org.pickly.service.member.service.dto.SearchMemberResultResDTO;

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

  List<SearchMemberResultResDTO> searchMemberByKeywords(String keyword, Long memberId,
      PageRequest pageRequest);
}
