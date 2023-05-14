package org.pickly.service.friend.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.pickly.service.friend.entity.QFriend;
import org.pickly.service.friend.repository.interfaces.FriendQueryRepository;
import org.pickly.service.friend.service.dto.FollowerResDTO;
import org.pickly.service.friend.service.dto.QFollowerResDTO;
import org.pickly.service.member.entity.QMember;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<FollowerResDTO> findAllFollowerByMember(final Long memberId) {
    QMember member = new QMember("member");
    QFriend follower = new QFriend("follower");
    QFriend friend = new QFriend("friend");

    return queryFactory
        .select(new QFollowerResDTO(
            friend.followee.id, follower, member
        ))
        .from(friend)
        .leftJoin(follower).on(
            follower.followee.id.eq(friend.follower.id),
            follower.follower.id.eq(friend.followee.id)
        )
        .innerJoin(member).on(friend.follower.id.eq(member.id))
        .where(
            friend.followee.id.eq(memberId)
        )
        .orderBy(member.id.asc())
        .fetch();
  }

}
