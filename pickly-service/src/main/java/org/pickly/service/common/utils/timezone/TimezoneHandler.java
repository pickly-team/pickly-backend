package org.pickly.service.common.utils.timezone;

import java.time.*;

public class TimezoneHandler {

  private static final ZoneId ZONE_ID = ZoneId.of("UTC");

  public static long convertToUnix(LocalDateTime dateTime, String timezone) {
    LocalDateTime zonedDateTime = convertToTimezone(dateTime, timezone);
    return zonedDateTime.toEpochSecond(ZoneId.of(timezone).getRules().getOffset(Instant.now()));
  }

  public static LocalDateTime convertToTimezone(LocalDateTime dateTime, String timezone) {
    ZoneId zoneId = ZoneId.of(timezone);
    ZonedDateTime timezoneDateTime = dateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId);
    return timezoneDateTime.toLocalDateTime();
  }

  public static LocalDateTime getUTCnow() {
    return LocalDateTime.now(ZONE_ID);
  }

  public static LocalDateTime getNowInTimezone(String timezone) {
    return LocalDateTime.now(ZoneId.of(timezone));
  }

}
