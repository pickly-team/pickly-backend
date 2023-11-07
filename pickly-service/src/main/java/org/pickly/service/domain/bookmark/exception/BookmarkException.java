package org.pickly.service.domain.bookmark.exception;

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

  public static class ForbiddenBookmarkException extends BookmarkException {
    public ForbiddenBookmarkException() {
      super(BookmarkErrorCode.FORBIDDEN_BOOKMARK);
    }
  }

  public static class InvalidReadStatusException extends BookmarkException {
    public InvalidReadStatusException() {
      super(BookmarkErrorCode.INVALID_READ_STATUS);
    }
  }

}
