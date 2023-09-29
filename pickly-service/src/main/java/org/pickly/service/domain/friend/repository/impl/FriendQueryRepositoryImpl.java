package org.pickly.service.domain.friend.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.domain.friend.repository.interfaces.FriendQueryRepository;
import org.pickly.service.domain.friend.entity.QFriend;
import org.pickly.service.domain.friend.dto.service.FollowerResDTO;
import org.pickly.service.domain.friend.dto.service.FollowingResDTO;
import org.pickly.service.domain.friend.dto.service.QFollowerResDTO;
import org.pickly.service.domain.friend.dto.service.QFollowingResDTO;
import org.pickly.service.domain.member.entity.QMember;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.pickly.service.domain.friend.entity.QFriend.friend;

@Repository
@RequiredArgsConstructor
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

  private final JPAQueryFactory queryFactory;
  private static final long CHECK_LAST = 1;

  @Override
  public List<FollowingResDTO> findAllFollowingByMember(Long memberId, PageRequest pageRequest) {
    QMember member = new QMember("member");
    QFriend follower = new QFriend("follower");
    QFriend friend = new QFriend("friend");

    String cursorId = (String) pageRequest.getCursorId();
    Integer size = pageRequest.getPageSize();

    return queryFactory
        .select(new QFollowingResDTO(member))
        .from(friend)
        .leftJoin(follower).on(
            follower.followee.id.eq(friend.follower.id),
            follower.follower.id.eq(friend.followee.id)
        )
        .innerJoin(member).on(friend.followee.id.eq(member.id))
        .where(
            friend.follower.id.eq(memberId),
            gtFolloweeUsername(cursorId)
        )
        .orderBy(friend.followee.username.asc())
        .limit(size + CHECK_LAST)
        .fetch();
  }

  @Override
  public List<FollowerResDTO> findAllFollowerWithOutBlockByMember(Long memberId, PageRequest pageRequest) {
    QMember member = new QMember("member");
    QFriend follower = new QFriend("follower");
    QFriend friend = new QFriend("friend");

    String cursorId = (String) pageRequest.getCursorId();
    Integer size = pageRequest.getPageSize();

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
            friend.followee.id.eq(memberId),
            gtFollowerUsername(cursorId)
        )
        .orderBy(friend.follower.username.asc())
        .limit(size + CHECK_LAST)
        .fetch();
  }

  private BooleanExpression gtFollowerUsername(final String cursorId) {
    if (cursorId == null) {
      return null;
    }
    return friend.follower.username.gt(cursorId);
  }

  private BooleanExpression gtFolloweeUsername(final String cursorId) {
    if (cursorId == null) {
      return null;
    }
    return friend.followee.username.gt(cursorId);
  }

}
