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

  public static class NicknameDuplicateException extends MemberException {
    public NicknameDuplicateException() {
      super(MemberErrorCode.NICKNAME_DUPLICATE_EXCEPTION);
    }
  }

  public static class CodeNotFoundException extends MemberException {
    public CodeNotFoundException() {
      super(MemberErrorCode.CODE_NOT_FOUND);
    }
  }

}
