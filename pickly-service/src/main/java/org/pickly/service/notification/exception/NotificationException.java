package org.pickly.service.notification.exception;

import org.pickly.common.error.exception.BusinessException;

public abstract class NotificationException extends BusinessException {

  protected NotificationException(NotificationErrorCode errorCode) {
    super(errorCode);
  }

  public static class NotificationStandardNotFoundException extends NotificationException {
    public NotificationStandardNotFoundException() {
      super(NotificationErrorCode.NOTIFICATION_STANDARD_NOT_FOUND);
    }
  }

  public static class NotificationStandardAlreadyExistException extends NotificationException {
    public NotificationStandardAlreadyExistException() {
      super(NotificationErrorCode.NOTIFICATION_STANDARD_ALREADY_EXIST);
    }
  }

  public static class NotificationNotFoundException extends NotificationException {
    public NotificationNotFoundException() {
      super(NotificationErrorCode.NOTIFICATION_NOT_FOUND);
    }
  }

}
