package org.pickly.service.bookmark.repository.impl;

import static org.pickly.service.bookmark.entity.QBookmark.bookmark;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.pickly.service.common.utils.page.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository {

  private final JPAQueryFactory queryFactory;

  private static final long CHECK_LAST = 1;

  @Override
  public List<Bookmark> findBookmarks(
      PageRequest pageRequest, Long memberId, Long categoryId, Boolean isUserLike,
      Boolean isUserRead
  ) {
    Long cursorId = pageRequest.getCursorId();
    Integer pageSize = pageRequest.getPageSize();
    return queryFactory
        .selectFrom(bookmark)
        .where(
            gtCursorId(cursorId),
            eqMemberId(memberId),
            eqCategoryId(categoryId),
            eqIsUserLike(isUserLike),
            eqIsUserRead(isUserRead)
        )
        .orderBy(bookmark.id.asc())
        .limit(pageSize + CHECK_LAST)
        .fetch();
  }

  @Override
  public long count(Long memberId, Boolean isUserLike) {
    return queryFactory
        .select(bookmark.count())
        .from(bookmark)
        .where(
            eqMemberId(memberId),
            eqIsUserLike(isUserLike)
        )
        .fetchFirst();
  }

  private BooleanExpression eqMemberId(final Long memberId) {
    if (memberId == null) {
      return null;
    }
    return bookmark.member.id.eq(memberId);
  }

  private BooleanExpression eqCategoryId(final Long categoryId) {
    if (categoryId == null) {
      return null;
    }
    return bookmark.category.id.eq(categoryId);
  }

  private BooleanExpression eqIsUserRead(final Boolean isUserRead) {
    if (isUserRead == null) {
      return null;
    }
    return bookmark.isUserRead.eq(isUserRead);
  }


  private BooleanExpression eqIsUserLike(final Boolean isUserLike) {
    if (isUserLike == null) {
      return null;
    }
    return bookmark.isUserLike.eq(isUserLike);
  }

  private BooleanExpression gtCursorId(final Long cursorId) {
    if (cursorId == null) {
      return null;
    }
    return bookmark.id.gt(cursorId);
  }

}
