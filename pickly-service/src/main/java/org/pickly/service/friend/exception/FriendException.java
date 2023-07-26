package org.pickly.service.friend.exception;

import org.pickly.common.error.exception.BusinessException;

public abstract class FriendException extends BusinessException {

  protected FriendException(FriendErrorCode errorCode) {
    super(errorCode);
  }

  public static class FriendNotFoundException extends FriendException {
    public FriendNotFoundException() {
      super(FriendErrorCode.FRIEND_NOT_FOUND);
    }
  }

}
