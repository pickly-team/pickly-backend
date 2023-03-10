package org.pickly.service.bookmark.repository.interfaces;

import java.util.List;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.common.utils.page.PageRequest;

public interface BookmarkQueryRepository {

  List<Bookmark> findBookmarks(PageRequest pageRequest, Long memberId, Long categoryId, Boolean isUserLike, Boolean readByUser, Visibility visibility);

  long count(Long memberId, Boolean isUserLike);
}
