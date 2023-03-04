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
      Boolean readByUser
  ) {
    Long cursorId = pageRequest.getCursorId();
    Integer pageSize = pageRequest.getPageSize();
    return queryFactory
        .selectFrom(bookmark)
        .where(
            ltCursorId(cursorId),
            eqMemberId(memberId),
            eqCategoryId(categoryId),
            eqIsUserLike(isUserLike),
            eqReadByUser(readByUser)
        )
        .orderBy(bookmark.id.desc())
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

  private BooleanExpression eqReadByUser(final Boolean readByUser) {
    if (readByUser == null) {
      return null;
    }
    return bookmark.readByUser.eq(readByUser);
  }


  private BooleanExpression eqIsUserLike(final Boolean isUserLike) {
    if (isUserLike == null) {
      return null;
    }
    return bookmark.isUserLike.eq(isUserLike);
  }

  private BooleanExpression ltCursorId(final Long cursorId) {
    if (cursorId == null) {
      return null;
    }
    return bookmark.id.lt(cursorId);
  }

}
