package org.pickly.service.comment.exception;

import org.pickly.common.error.exception.BusinessException;

public abstract class CommentException extends BusinessException {

  protected CommentException(CommentErrorCode errorCode) {
    super(errorCode);
  }

  public static class CommentNotFoundException extends CommentException {
    public CommentNotFoundException() {
      super(CommentErrorCode.COMMENT_NOT_FOUND);
    }
  }

}
