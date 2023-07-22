package org.pickly.service.common.utils.timezone;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimezoneHandler {

  private static final ZoneOffset OFFSET = ZoneOffset.of("+09:00");
  private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");


  public static long convertToUnix(LocalDateTime dateTime) {
    ZonedDateTime kstDateTime = dateTime.atZone(OFFSET);
    return kstDateTime.toEpochSecond();
  }

  public static LocalDateTime getNowByZone() {
    return LocalDateTime.now(ZONE_ID);
  }

}
