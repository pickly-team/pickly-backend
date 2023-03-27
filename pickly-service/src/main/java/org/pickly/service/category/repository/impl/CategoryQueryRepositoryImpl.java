package org.pickly.service.category.repository.impl;

import static org.pickly.service.category.entity.QCategory.category;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.repository.interfaces.CategoryQueryRepository;
import org.pickly.service.common.utils.page.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepositoryImpl implements CategoryQueryRepository {

  private final JPAQueryFactory queryFactory;

  private static final long CHECK_LAST = 1;
  @Override
  public List<Category> findAllByMemberId(PageRequest pageRequest, Long memberId) {
    Long cursorId = pageRequest.getCursorId();
    Integer pageSize = pageRequest.getPageSize();

    return queryFactory
        .selectFrom(category)
        .where(
            ltCursorId(cursorId),
            eqMemberId(memberId)
        )
        .orderBy(category.id.desc())
        .limit(pageSize + CHECK_LAST)
        .fetch();
  }

  private BooleanExpression eqMemberId(final Long memberId) {
    if (memberId == null) {
      return null;
    }
    return category.member.id.eq(memberId);
  }

  private BooleanExpression ltCursorId(final Long cursorId) {
    if (cursorId == null) {
      return null;
    }
    return category.id.lt(cursorId);
  }
}
