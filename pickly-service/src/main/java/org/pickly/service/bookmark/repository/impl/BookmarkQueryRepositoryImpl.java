package org.pickly.service.bookmark.repository.impl;

import static org.pickly.service.bookmark.entity.QBookmark.bookmark;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Bookmark> findBookmarks(Long cursorId, Long memberId, Boolean isUserLike, Integer pageSize) {
    return queryFactory
        .selectFrom(bookmark)
        .where(
            gtCursorId(cursorId),
            eqMemberId(memberId),
            eqIsUserLike(isUserLike)
        )
        .orderBy(bookmark.id.asc())
        .limit(pageSize + 1)
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
