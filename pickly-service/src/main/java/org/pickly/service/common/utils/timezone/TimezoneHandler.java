package org.pickly.service.common.utils.timezone;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimezoneHandler {

  // DB에 UTC로 저장되고 화면에는 유저의 소속 시간대 기준으로 줘야 함
  private static final ZoneOffset OFFSET = ZoneOffset.of("+09:00");
  private static final ZoneId ZONE_ID = ZoneId.of("UTC");

  public static long convertToUnix(LocalDateTime dateTime) {
    return dateTime.atZone(OFFSET).toEpochSecond();
  }

  public static LocalDateTime getUTCnow() {
    return LocalDateTime.now(ZONE_ID);
  }

  public static LocalDateTime getNowInTimezone(String timezone) {
    return LocalDateTime.now(ZoneId.of(timezone));
  }

}
