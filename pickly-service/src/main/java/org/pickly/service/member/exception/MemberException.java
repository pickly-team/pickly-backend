package org.pickly.service.member.exception;

import org.pickly.service.common.error.exception.BusinessException;

public abstract class MemberException extends BusinessException {

  protected MemberException(MemberErrorCode errorCode) {
    super(errorCode);
  }

  public static class MemberNotFoundException extends MemberException {
    public MemberNotFoundException() {
      super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
  }

}
