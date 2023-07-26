package org.pickly.service.friend.exception;

import org.pickly.service.common.error.exception.BusinessException;

public abstract class FriendException extends BusinessException {

  protected FriendException(FriendErrorCode errorCode) {
    super(errorCode);
  }

  public static class FriendNotFoundException extends FriendException {
    public FriendNotFoundException() {
      super(FriendErrorCode.FRIEND_NOT_FOUND);
    }
  }

  public static class AlreadyFriendException extends FriendException {
    public AlreadyFriendException() {
      super(FriendErrorCode.ALREADY_FRIEND);
    }
  }

}
