package org.pickly.service.common.utils.timezone;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimezoneHandler {

  // DB에 UTC로 저장되고 화면에는 KST 기준으로 줘야 함
  private static final ZoneOffset OFFSET = ZoneOffset.of("+09:00");
  private static final ZoneId ZONE_ID = ZoneId.of("UTC");
  private static final ZoneId KST_ZONE_ID = ZoneId.of("Asia/Seoul");


  public static long convertToUnix(LocalDateTime dateTime) {
    return dateTime.atZone(OFFSET).toEpochSecond();
  }

  public static LocalDateTime getUTCnow() {
    return LocalDateTime.now(ZONE_ID);
  }

  public static LocalDateTime getKSTnow() {
    return LocalDateTime.now(KST_ZONE_ID);
  }

}
