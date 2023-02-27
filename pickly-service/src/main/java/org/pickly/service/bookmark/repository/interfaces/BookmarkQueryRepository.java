package org.pickly.service.bookmark.repository.interfaces;

import java.util.List;
import org.pickly.service.bookmark.entity.Bookmark;

public interface BookmarkQueryRepository {

  List<Bookmark> findBookmarks(Long cursorId, Long memberId, Long categoryId, Boolean isUserLike, Boolean isUserRead, Integer pageSize);

  long count(Long memberId, Boolean isUserLike);
}
