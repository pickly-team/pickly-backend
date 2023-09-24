package org.pickly.service.domain.comment.exception;

import org.pickly.service.common.error.exception.BusinessException;

public abstract class CommentException extends BusinessException {

  protected CommentException(CommentErrorCode errorCode) {
    super(errorCode);
  }

  public static class CommentNotFoundException extends CommentException {
    public CommentNotFoundException() {
      super(CommentErrorCode.COMMENT_NOT_FOUND);
    }
  }

  public static class OnlyAuthorCanEditException extends CommentException {
    public OnlyAuthorCanEditException() {
      super(CommentErrorCode.ONLY_AUTHOR_CAN_EDIT);
    }
  }

}
