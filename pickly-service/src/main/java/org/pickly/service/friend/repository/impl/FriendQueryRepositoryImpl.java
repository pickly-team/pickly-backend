package org.pickly.service.friend.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.pickly.service.friend.repository.interfaces.FriendQueryRepository;
import org.pickly.service.friend.service.dto.FollowerResDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<FollowerResDTO> findAllFollowerByMember(Long memberId) {
    return null;
  }

}
