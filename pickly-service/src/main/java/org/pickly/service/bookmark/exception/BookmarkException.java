package org.pickly.service.bookmark.exception;

import org.pickly.service.common.error.exception.BusinessException;

public abstract class BookmarkException extends BusinessException {

  protected BookmarkException(BookmarkErrorCode errorCode) {
    super(errorCode);
  }

  public static class BookmarkNotFoundException extends BookmarkException {
    public BookmarkNotFoundException() {
      super(BookmarkErrorCode.BOOKMARK_NOT_FOUND);
    }
  }

}
