package org.pickly.service.member.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.pickly.service.member.repository.interfaces.MemberQueryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.pickly.service.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

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

}
