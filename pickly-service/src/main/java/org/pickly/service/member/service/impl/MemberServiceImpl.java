package org.pickly.service.member.service.impl;

import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.pickly.service.block.repository.interfaces.BlockRepository;
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.common.utils.base.AuthTokenUtil;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.friend.repository.interfaces.FriendRepository;
import org.pickly.service.member.common.MemberMapper;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.exception.MemberException;
import org.pickly.service.member.repository.interfaces.MemberQueryRepository;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.pickly.service.member.service.dto.*;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.notification.entity.NotificationStandard;
import org.pickly.service.notification.exception.NotificationException;
import org.pickly.service.notification.repository.interfaces.NotificationRepository;
import org.pickly.service.notification.repository.interfaces.NotificationStandardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final MemberQueryRepository memberQueryRepository;
  private final FriendRepository friendRepository;
  private final MemberMapper memberMapper;
  private final BookmarkRepository bookmarkRepository;
  private final BlockRepository blockRepository;
  private final NotificationRepository notificationRepository;
  private final NotificationStandardRepository notificationStandardRepository;
  private final AuthTokenUtil authTokenUtil;

  @Transactional(readOnly = true)
  public NotificationStandard findNotificationStandardByMemberId(final Long memberId) {
    return notificationStandardRepository.findByMemberId(memberId)
        .orElseThrow(NotificationException.NotificationStandardNotFoundException::new);
  }

  @Override
  public void existsById(Long memberId) {
    if (!memberRepository.existsByIdAndDeletedAtIsNull(memberId)) {
      throw new MemberException.MemberNotFoundException();
    }
  }

  public boolean existsByEmail(String email) {
    return memberRepository.existsByEmailAndDeletedAtIsNull(email);
  }

  public boolean existsByNickname(String nickname) {
    return memberRepository.existsByNicknameAndDeletedAtIsNull(nickname);
  }

  public boolean existsByUsername(String username) {
    return memberRepository.existsByUsernameAndDeletedAtIsNull(username);
  }

  @Override
  @Transactional
  public void updateMyProfile(Long memberId, MemberProfileUpdateDTO request) {
    Member member = findById(memberId);
    if (existsByUsername(request.getNickname())) {
      throw new MemberException.NicknameDuplicateException();
    }
    member.updateProfile(
        request.getName(),
        request.getNickname(),
        request.getProfileEmoji()
    );
  }

  @Override
  @Transactional
  public MemberRegisterDto register(String token) {
    FirebaseToken decodedToken = authTokenUtil.validateToken(token);
    Member member = memberMapper.tokenToMember(decodedToken);
    if (existsByEmail(member.getEmail())) {
      return updateTokenExistMember(member.getEmail());
    } else {
      return saveNewMember(member);
    }
  }

  private MemberRegisterDto updateTokenExistMember(String email) {
    Member savedMember = findByEmail(email);
    return memberMapper.toMemberRegisterDTO(savedMember);
  }

  private MemberRegisterDto saveNewMember(Member member) {
    memberRepository.save(member);
    createNotificationStandard(member);
    return memberMapper.toMemberRegisterDTO(member);
  }

  private void createNotificationStandard(Member member) {
    notificationStandardRepository.save(
        NotificationStandard.createDafaultStandard(member)
    );
  }

  @Override
  public MemberProfileDTO getMemberIdByToken(String token) {
    FirebaseToken decodedToken = authTokenUtil.validateToken(token);
    Member member = memberRepository.findByEmail(decodedToken.getEmail())
        .orElseThrow(MemberException.MemberNotFoundException::new);
    return memberMapper.toMemberProfileDTO(member, false, false);
  }

  @Override
  @Transactional(readOnly = true)
  public List<SearchMemberResultResDTO> searchMemberByKeywords(String keyword, Long memberId,
      PageRequest pageRequest) {
    existsById(memberId);

    List<SearchMemberResultResDTO> searchMemberResults = memberQueryRepository.findAllMembersByKeyword(
        keyword, memberId, pageRequest);

    return searchMemberResults.stream().peek(searchMemberResult -> {
      boolean isFollowing = friendRepository.existsByFollowerIdAndFolloweeId(memberId,
          searchMemberResult.getMemberId());
      boolean isBlocked = blockRepository.existsByBlockerIdAndBlockeeId(memberId,
          searchMemberResult.getMemberId());

      searchMemberResult.setFollowingFlag(isFollowing);
      searchMemberResult.setBlockedFlag(isBlocked);
    }).toList();
  }

  @Override
  public Map<Long, String> findTokenByIds(List<Long> memberIds) {
    return memberQueryRepository.findTokenByIds(memberIds);
  }

  @Override
  @Transactional(readOnly = true)
  public MyProfileDTO findMyProfile(final Long memberId) {
    Member member = findById(memberId);
    Long followersCount = friendRepository.countByFolloweeId(memberId);
    Long followeesCount = friendRepository.countByFollowerId(memberId);
    Long bookmarksCount = bookmarkRepository.countByMemberIdAndDeletedAtNull(memberId);
    return memberMapper.toMyProfileDTO(member, followersCount, followeesCount, bookmarksCount);
  }

  @Override
  @Transactional(readOnly = true)
  public MemberProfileDTO findProfileById(final Long loginId, final Long memberId) {
    boolean isFollowing = friendRepository.existsByFollowerIdAndFolloweeId(loginId, memberId);
    boolean isBlocked = blockRepository.existsByBlockerIdAndBlockeeId(loginId, memberId);
    Member member = findById(memberId);
    return memberMapper.toMemberProfileDTO(member, isFollowing, isBlocked);
  }

  @Override
  @Transactional
  public HardModeDTO setHardMode(Long memberId, MemberStatusDTO request) {
    Member member = findById(memberId);

    member.setHardMode(request.getIsHardMode());
    return memberMapper.toMemberStatusDTO(member.isHardMode(member.getIsHardMode()));
  }

  @Transactional(readOnly = true)
  public MemberModeDTO findModeByMemberId(final Long memberId) {
    Member member = findById(memberId);
    NotificationStandard standard = findNotificationStandardByMemberId(memberId);
    return memberMapper.toMemberModeDTO(member, standard);
  }

  @Override
  @Transactional(readOnly = true)
  public Member findById(Long id) {
    return memberRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(MemberException.MemberNotFoundException::new);
  }

  @Transactional(readOnly = true)
  public Member findByEmail(String email) {
    return memberRepository.findByEmailAndDeletedAtIsNull(email)
        .orElseThrow(MemberException.MemberNotFoundException::new);
  }

  @Override
  public void deleteMember(Long memberId) {
    Member member = findById(memberId);
    memberRepository.delete(member);
    notificationRepository.deleteAllByMemberId(memberId);
  }

  @Override
  public void updateNotificationSettings(Long memberId, String timezone, String fcmToken) {
    Member member = findById(memberId);
    member.updateNotificationSettings(fcmToken, timezone);
  }
}
