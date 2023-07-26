package org.pickly.service.block.exception;

import org.pickly.common.error.exception.BusinessException;

public abstract class BlockException extends BusinessException {

  protected BlockException(BlockErrorCode errorCode) {
    super(errorCode);
  }

  public static class BlockNotFoundException extends BlockException {
    public BlockNotFoundException() {
      super(BlockErrorCode.BLOCK_NOT_FOUND);
    }
  }

  public static class AlreadyBlockException extends BlockException {
    public AlreadyBlockException() {
      super(BlockErrorCode.ALREADY_BLOCK);
    }
  }

}
