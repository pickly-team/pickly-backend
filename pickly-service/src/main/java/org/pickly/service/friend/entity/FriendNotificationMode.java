package org.pickly.service.friend.entity;

import lombok.Getter;

@Getter
public enum FriendNotificationMode {
  ALLOWED(true),
  NOT_ALLOWED(false);

  private final boolean isNotificationAllowed;

  FriendNotificationMode(boolean isNotificationAllowed) {
    this.isNotificationAllowed = isNotificationAllowed;
  }
}
