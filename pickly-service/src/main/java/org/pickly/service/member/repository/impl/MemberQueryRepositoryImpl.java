package org.pickly.service.member.repository.impl;

import static org.pickly.service.member.entity.QMember.member;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.member.repository.interfaces.MemberQueryRepository;
import org.pickly.service.member.service.dto.SearchMemberResultResDTO;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

  private static final long CHECK_LAST = 1;
  private final JPAQueryFactory queryFactory;

  @Override
  public List<SearchMemberResultResDTO> findAllMembersByKeyword(String keyword, Long memberId,
      PageRequest pageRequest) {
    String cursorId = (String) pageRequest.getCursorId();
    Integer size = pageRequest.getPageSize();

    return queryFactory.select(
            Projections.constructor(SearchMemberResultResDTO.class,
                member.id,
                member.username,
                member.profileEmoji
            )
        ).from(member)
        .where(
            member.username.contains(keyword),
            member.id.ne(memberId),
            gtMemberUsername(cursorId)
        )
        .orderBy(member.username.asc())
        .limit(size + CHECK_LAST)
        .fetch();
  }

  private BooleanExpression gtMemberUsername(final String cursorId) {
    if (cursorId == null) {
      return null;
    }
    return member.username.gt(cursorId);
  }

}
