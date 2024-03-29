package org.pickly.service.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.domain.block.repository.interfaces.BlockRepository;
import org.pickly.service.domain.friend.repository.interfaces.FriendRepository;
import org.pickly.service.domain.member.dto.service.SearchMemberResultResDTO;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.repository.interfaces.MemberQueryRepository;
import org.pickly.service.domain.member.repository.interfaces.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.pickly.service.domain.member.exception.MemberException.MemberNotFoundException;
import static org.pickly.service.domain.member.exception.MemberException.NicknameDuplicateException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReadService {

  private final MemberRepository memberRepository;
  private final MemberQueryRepository memberQueryRepository;
  private final FriendRepository friendRepository;
  private final BlockRepository blockRepository;

  public void existsById(Long memberId) {
    if (!memberRepository.existsByIdAndDeletedAtIsNull(memberId)) {
      throw new MemberNotFoundException();
    }
  }

  public boolean existsByEmail(String email) {
    return memberRepository.existsByEmailAndDeletedAtIsNull(email);
  }

  public boolean existsByNickname(String nickname) {
    return memberRepository.existsByNicknameAndDeletedAtIsNull(nickname);
  }

  public void isValidNickname(Member member, String newNickname) {
    if (
        checkIsNewNickname(member, newNickname) && existsByNickname(newNickname)
    ) {
      throw new NicknameDuplicateException();
    }
  }

  public boolean checkIsNewNickname(Member member, String newNickname) {
    return member.getNickname() != null && !member.getNickname().equals(newNickname);
  }

  public List<SearchMemberResultResDTO> searchMemberByKeywords(
      String keyword, Long memberId, PageRequest pageRequest
  ) {
    existsById(memberId);

    List<SearchMemberResultResDTO> searchMemberResults = memberQueryRepository.findAllMembersByKeyword(
        keyword, memberId, pageRequest);

    // FIXME: for문 돌면서 추가 쿼리가 2번씩 나가는 상황 -> projection 이용해서 개선할 수 없는지 확인 필요
    return searchMemberResults.stream().peek(searchMemberResult -> {
      boolean isFollowing = friendRepository.existsByFollowerIdAndFolloweeId(memberId,
          searchMemberResult.getMemberId());
      boolean isBlocked = blockRepository.existsByBlockerIdAndBlockeeId(memberId,
          searchMemberResult.getMemberId());

      searchMemberResult.setFollowingFlag(isFollowing);
      searchMemberResult.setBlockedFlag(isBlocked);
    }).toList();
  }

  public Map<Long, String> findTokenByIds(List<Long> memberIds) {
    return memberQueryRepository.findTokenByIds(memberIds);
  }

  public Member findById(Long id) {
    return memberRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(MemberNotFoundException::new);
  }

  public Member findByEmail(String email) {
    return memberRepository.findByEmailAndDeletedAtIsNull(email)
        .orElseThrow(MemberNotFoundException::new);
  }

  public List<Member> findLongTermInactive(final LocalDate current) {
    final LocalDateTime inactiveStandard = current.minusMonths(2).atTime(0, 0);
    return memberRepository.findByStatusLastLoginAtBefore(inactiveStandard);
  }

}
