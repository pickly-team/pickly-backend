package org.pickly.service.domain.bookmark.repository.interfaces;

import java.util.List;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.entity.Visibility;
import org.pickly.service.common.utils.page.PageRequest;

public interface BookmarkQueryRepository {

  List<Bookmark> findBookmarks(PageRequest pageRequest, Long memberId, Long categoryId,
                               Boolean isUserLike, Boolean readByUser, Visibility visibility);

  long count(Long memberId, Boolean isUserLike);

  List<Bookmark> findBookmarkByCategoryId(PageRequest pageRequest, Long categoryId);

  List<Bookmark> findAllUnreadBookmark();
}
