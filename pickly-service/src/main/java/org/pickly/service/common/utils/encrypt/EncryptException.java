package org.pickly.service.common.utils.encrypt;

import org.pickly.service.common.error.CommonErrorCode;
import org.pickly.service.common.error.exception.BusinessException;

public abstract class EncryptException extends BusinessException {

  protected EncryptException(CommonErrorCode errorCode) {
    super(errorCode);
  }

  public static class FailToEncryptException extends EncryptException {
    public FailToEncryptException() {
      super(CommonErrorCode.FAIL_TO_ENCRYPT);
    }
  }

  public static class FailToDecryptException extends EncryptException {
    public FailToDecryptException() {
      super(CommonErrorCode.FAIL_TO_DECRYPT);
    }
  }

}
