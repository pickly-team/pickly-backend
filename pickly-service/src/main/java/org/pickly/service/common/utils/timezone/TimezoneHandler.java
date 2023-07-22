package org.pickly.service.common.utils.timezone;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimezoneHandler {

  // DB에 KST로 저장되고 화면에도 KST 기준으로 줘야 함
  private static final ZoneOffset OFFSET = ZoneOffset.of("+00:00");
  private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");


  public static long convertToUnix(LocalDateTime dateTime) {
    return dateTime.atZone(OFFSET).toEpochSecond();
  }

  public static LocalDateTime getNowByZone() {
    return LocalDateTime.now(ZONE_ID);
  }

}
