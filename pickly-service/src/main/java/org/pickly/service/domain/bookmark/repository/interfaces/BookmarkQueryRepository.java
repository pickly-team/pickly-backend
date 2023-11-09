package org.pickly.service.domain.bookmark.repository.interfaces;

import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.entity.Visibility;
import org.pickly.service.domain.bookmark.vo.BookmarkReadStatus;
import org.pickly.service.domain.category.entity.Category;

import java.util.List;
import java.util.Map;

public interface BookmarkQueryRepository {

  List<Bookmark> findBookmarks(PageRequest pageRequest, Long memberId, Long categoryId,
                               Boolean isUserLike, Boolean readByUser, List<Visibility> visibilities);

  long count(Long memberId, Boolean isUserLike);

  List<Bookmark> findBookmarkByCategoryId(PageRequest pageRequest, Long categoryId);

  List<Bookmark> findAllUnreadBookmark();

  Map<Category, BookmarkReadStatus> findCategoryReadStatus(Long memberId);

}
