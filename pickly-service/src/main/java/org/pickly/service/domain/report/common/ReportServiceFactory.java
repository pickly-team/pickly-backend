package org.pickly.service.domain.report.common;

import org.pickly.service.domain.report.service.ReportService;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class ReportServiceFactory {

  private final Map<ReportType, ReportService> reportServiceMap = new EnumMap<>(ReportType.class);

  public ReportServiceFactory(List<ReportService> reportServices) {
    reportServices.forEach(s -> reportServiceMap.put(s.getReportType(), s));
  }

  public ReportService getReportService(ReportType type) {
    return reportServiceMap.get(type);
  }

}
