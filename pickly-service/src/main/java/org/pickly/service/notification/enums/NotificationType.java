package org.pickly.service.notification.enums;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum NotificationType {

  NORMAL(1),
  HARD(2),
  UNKNOWN(0);

  private final int type;

  private static final Map<Integer, NotificationType> typeMap = Collections.unmodifiableMap(
      Stream.of(values())
          .collect(Collectors.toMap(
              NotificationType::getType,
              Function.identity()
          ))
  );

  NotificationType(int type) {
    this.type = type;
  }

  public int getType() {
    return this.type;
  }

  public static NotificationType find(Integer type) {
    return Optional.ofNullable(typeMap.get(type)).orElse(UNKNOWN);
  }

}
