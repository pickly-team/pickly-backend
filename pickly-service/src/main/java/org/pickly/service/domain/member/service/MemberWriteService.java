package org.pickly.service.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.common.config.CacheConfig;
import org.pickly.service.common.utils.encrypt.EncryptService;
import org.pickly.service.common.utils.encrypt.ExtensionKey;
import org.pickly.service.domain.member.entity.Member;
import org.pickly.service.domain.member.exception.MemberException;
import org.pickly.service.domain.member.repository.interfaces.MemberRepository;
import org.pickly.service.domain.member.service.dto.MemberProfileUpdateDTO;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberWriteService {

  private final MemberRepository memberRepository;
  private final CacheManager cacheManager;
  private final EncryptService encryptService;

  public Member create(Member member) {
    return memberRepository.save(member);
  }

  public void delete(Member member) {
    memberRepository.delete(member);
  }

  public Member setHardMode(Member member, boolean isHardMode) {
    member.setHardMode(isHardMode);
    return member;
  }

  public void updateNotificationSetting(Member member, String timezone, String fcmToken) {
    member.updateNotificationSettings(fcmToken, timezone);
  }

  public void update(Member member, MemberProfileUpdateDTO request) {
    member.updateProfile(
        request.getName(),
        request.getNickname(),
        request.getProfileEmoji()
    );
  }

  public String makeAuthenticationCode(Long memberId) {
    String authenticationCode = makeRandomCode();
    getCodeCache().put(authenticationCode, memberId);
    return authenticationCode;
  }

  public String checkMemberAuthenticationCode(String code) {
    Long memberId = getCodeCache().get(code, Long.class);
    if (memberId == null) {
      throw new MemberException.CodeNotFoundException();
    }
    ExtensionKey key = encryptService.getKey();
    String result = key.encrypt(memberId);
    getCodeCache().evict(key);
    return result;
  }

  private Cache getCodeCache() {
    return cacheManager.getCache(CacheConfig.AUTHENTICATE);
  }

  private String makeRandomCode() {
    String authenticationCode;
    do {
      Random random = new Random();
      int randomNumber = random.nextInt(1000000); // 0 이상 999999 이하의 난수 생성
      authenticationCode = String.format("%06d", randomNumber);
    } while (cacheManager.getCache(CacheConfig.AUTHENTICATE).get(authenticationCode) != null);

    return authenticationCode;
  }

}
