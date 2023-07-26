package org.pickly.service.report.exception;

import org.pickly.service.common.error.exception.BusinessException;

public abstract class ReportException extends BusinessException {

  protected ReportException(ReportErrorCode errorCode) {
    super(errorCode);
  }

  public static class ReportNotFoundException extends ReportException {
    public ReportNotFoundException() {
      super(ReportErrorCode.REPORT_NOT_FOUND);
    }
  }

  public static class AlreadyReportException extends ReportException {
    public AlreadyReportException() {
      super(ReportErrorCode.ALREADY_REPORT);
    }
  }

  public static class CannotReportSelfException extends ReportException {
    public CannotReportSelfException() {
      super(ReportErrorCode.CAN_NOT_REPORT_SELF);
    }
  }

}
