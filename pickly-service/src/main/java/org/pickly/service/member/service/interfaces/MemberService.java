package org.pickly.service.member.service.interfaces;

import org.pickly.service.common.utils.page.PageRequest;
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

  List<SearchMemberResultResDTO> searchMemberByKeywords(String keyword, Long memberId,
      PageRequest pageRequest);

  MemberProfileDTO getMemberIdByToken(String token);

  void updateNotificationSettings(Long memberId, String timezone, String fcmToken);

  String makeMemberAuthenticationCode(Long memberId);

  String checkMemberAuthenticationCode(String code);

}
