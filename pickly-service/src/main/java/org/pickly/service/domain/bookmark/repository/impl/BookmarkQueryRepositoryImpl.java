package org.pickly.service.domain.bookmark.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.bookmark.entity.Visibility;
import org.pickly.service.domain.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.pickly.service.domain.bookmark.vo.BookmarkReadStatus;
import org.pickly.service.domain.category.entity.Category;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.pickly.service.domain.bookmark.entity.QBookmark.bookmark;
import static org.pickly.service.domain.category.entity.QCategory.category;
import static org.pickly.service.domain.notification.entity.QNotification.notification;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookmarkQueryRepositoryImpl implements BookmarkQueryRepository {

  private final JPAQueryFactory queryFactory;

  private static final long CHECK_LAST = 1;

  @Override
  public List<Bookmark> findBookmarks(
      PageRequest pageRequest, Long memberId, Long categoryId, Boolean isUserLike,
      Boolean readByUser, List<Visibility> visibilities
  ) {
    Long cursorId = (Long) pageRequest.getCursorId();
    Integer pageSize = pageRequest.getPageSize();
    return queryFactory
        .selectFrom(bookmark)
        .leftJoin(bookmark.category, category)
        .fetchJoin()
        .where(
            ltCursorId(cursorId),
            eqMemberId(memberId),
            eqCategoryId(categoryId),
            eqIsUserLike(isUserLike),
            eqReadByUser(readByUser),
            inVisibility(visibilities),
            notDeleted()
        )
        .orderBy(bookmark.id.desc())
        .limit(pageSize + CHECK_LAST)
        .fetch();
  }

  @Override
  public List<Bookmark> searchBookmarks(
      PageRequest pageRequest, Long memberId, String keyword
  ) {
    Long cursorId = (Long) pageRequest.getCursorId();
    Integer pageSize = pageRequest.getPageSize();
    return queryFactory
        .selectFrom(bookmark)
        .leftJoin(bookmark.category, category)
        .fetchJoin()
        .where(
            ltCursorId(cursorId),
            eqMemberId(memberId),
            bookmark.title.containsIgnoreCase(keyword),
            notDeleted()
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
            eqIsUserLike(isUserLike),
            notDeleted()
        )
        .fetchFirst();
  }

  @Override
  public List<Bookmark> findBookmarkByCategoryId(PageRequest pageRequest, Long categoryId) {

    Long cursorId = (Long) pageRequest.getCursorId();
    Integer pageSize = pageRequest.getPageSize();

    return queryFactory
        .selectFrom(bookmark)
        .where(
            ltCursorId(cursorId),
            eqCategoryId(categoryId),
            notDeleted()
        )
        .orderBy(bookmark.id.desc())
        .limit(pageSize + CHECK_LAST)
        .fetch();
  }

  @Override
  public List<Bookmark> findAllUnreadBookmark() {
    LocalDate date = TimezoneHandler.getUTCnow().toLocalDate();
    LocalDateTime startDateTime = date.atTime(0, 0);
    LocalDateTime endDateTime = startDateTime.plusDays(1);

    return queryFactory
        .selectFrom(bookmark)
        .where(
            JPAExpressions
                .select(notification.bookmarkId)
                .from(notification)
                .where(
                    notification.bookmarkId.eq(bookmark.id)
                        .and(notification.sendDateTime.goe(startDateTime))
                        .and(notification.sendDateTime.lt(endDateTime))
                        .and(notDeleted())
                ).notExists()
                .and(bookmark.readByUser.eq(false))
        )
        .where(bookmark.member.status.isInactive.eq(false))
        .fetch();
  }

  @Override
  public Map<Category, BookmarkReadStatus> findCategoryReadStatus(final Long memberId) {
    Map<Category, BookmarkReadStatus> categoryBookmarkCounts = new LinkedHashMap<>();
    List<Tuple> results = queryFactory
        .select(
            category,
            bookmark.id.count(),
            sumReadTrue()
        )
        .from(category)
        .leftJoin(bookmark).on(category.id.eq(bookmark.category.id)
            .and(bookmark.deletedAt.isNull()))
        .where(
            category.member.id.eq(memberId),
            category.deletedAt.isNull()
        )
        .groupBy(category.id)
        .orderBy(category.orderNum.asc())
        .fetch();

    for (Tuple result : results) {
      Category currentCategory = result.get(category);
      Long totalBookmarks = result.get(bookmark.id.count());
      Long readBookmarks = result.get(sumReadTrue());

      BookmarkReadStatus status = new BookmarkReadStatus(totalBookmarks, readBookmarks);
      categoryBookmarkCounts.put(currentCategory, status);
    }
    return categoryBookmarkCounts;
  }

  private NumberExpression<Long> sumReadTrue() {
    return new CaseBuilder()
        .when(bookmark.readByUser.isTrue())
        .then(1L)
        .otherwise(0L)
        .sum();
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

  private BooleanExpression inVisibility(final List<Visibility> visibilities) {
    if (visibilities == null) {
      return null;
    }
    return bookmark.visibility.in(visibilities);
  }

  private BooleanExpression notDeleted() {
    return bookmark.deletedAt.isNull();
  }

  private BooleanExpression ltCursorId(final Long cursorId) {
    if (cursorId == null) {
      return null;
    }
    return bookmark.id.lt(cursorId);
  }

}
