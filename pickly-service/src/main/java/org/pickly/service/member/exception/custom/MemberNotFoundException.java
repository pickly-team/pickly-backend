package org.pickly.service.member.exception.custom;

public class MemberNotFoundException extends RuntimeException {
  public MemberNotFoundException(Long memberId) {
    super("요청한 사용자가 존재하지 않습니다. memberId : " + memberId);
  }
}