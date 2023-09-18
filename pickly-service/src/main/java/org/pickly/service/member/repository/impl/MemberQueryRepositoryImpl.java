package org.pickly.service.member.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.member.repository.interfaces.MemberQueryRepository;
import org.pickly.service.member.service.dto.SearchMemberResultResDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.pickly.service.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

  private static final long CHECK_LAST = 1;
  private final JPAQueryFactory queryFactory;

  @Override
  public Map<Long, String> findTokenByIds(List<Long> memberIds) {
    return queryFactory
        .select(member.id, member.fcmToken)
        .from(member)
        .where(member.id.in(memberIds))
        .fetch()
        .stream()
        .collect(Collectors.toMap(
            tuple -> tuple.get(member.id),
            tuple -> Optional.ofNullable(tuple.get(member.fcmToken)).orElse("")
        ));
  }

  @Override
  public List<SearchMemberResultResDTO> findAllMembersByKeyword(String keyword, Long memberId,
      PageRequest pageRequest) {
    Long cursorId = (Long) pageRequest.getCursorId();
    Integer size = pageRequest.getPageSize();

    return queryFactory.select(
            Projections.constructor(SearchMemberResultResDTO.class,
                member.id,
                member.nickname,
                member.profileEmoji
            )
        ).from(member)
        .where(
            member.nickname.containsIgnoreCase(keyword),
            member.id.ne(memberId),
            ltMemberId(cursorId)
        )
        .orderBy(member.nickname.asc())
        .limit(size + CHECK_LAST)
        .fetch();
  }

  private BooleanExpression ltMemberId(final Long cursorId) {
    if (cursorId == null) {
      return null;
    }
    return member.id.lt(cursorId);
  }

}
