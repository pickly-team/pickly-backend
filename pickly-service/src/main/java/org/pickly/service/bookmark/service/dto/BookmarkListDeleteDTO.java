package org.pickly.service.bookmark.service.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkListDeleteDTO {

  private final List<Long> bookmarkIds;
}
