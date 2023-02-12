package org.pickly.service.bookmark.entity;

import lombok.Getter;

@Getter
public enum NoticeScope {

  SCOPE_PRIVATE("나만 보기"),
  SCOPE_PUBLIC("전체 공개");

  private String description;

  NoticeScope(String description) {
    this.description = description;
  }

}