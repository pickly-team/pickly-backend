package org.pickly.service.friend.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.friend.entity.QFriend;
import org.pickly.service.friend.repository.interfaces.FriendQueryRepository;
import org.pickly.service.friend.service.dto.FollowerResDTO;
import org.pickly.service.friend.service.dto.FollowingResDTO;
import org.pickly.service.friend.service.dto.QFollowerResDTO;
import org.pickly.service.friend.service.dto.QFollowingResDTO;
import org.pickly.service.member.entity.QMember;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.pickly.service.friend.entity.QFriend.friend;

@Repository
@RequiredArgsConstructor
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

  private final JPAQueryFactory queryFactory;
  private static final long CHECK_LAST = 1;

  @Override
  public List<FollowerResDTO> findAllFollowerByMember(final Long memberId, final PageRequest pageRequest) {
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
            follower.follower.id.eq(friend.followee.id),
            ltFollowerUsername(cursorId)
        )
        .innerJoin(member).on(friend.follower.id.eq(member.id))
        .where(
            friend.followee.id.eq(memberId)
        )
        .orderBy(friend.follower.username.asc())
        .limit(size + CHECK_LAST)
        .fetch();
  }

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
            follower.follower.id.eq(friend.followee.id),
            ltFolloweeUsername(cursorId)
        )
        .innerJoin(member).on(friend.followee.id.eq(member.id))
        .where(
            friend.follower.id.eq(memberId)
        )
        .orderBy(friend.followee.username.asc())
        .limit(size + CHECK_LAST)
        .fetch();
  }

  private BooleanExpression ltFollowerUsername(final String cursorId) {
    if (cursorId == null) {
      return null;
    }
    return friend.follower.username.lt(cursorId);
  }

  private BooleanExpression ltFolloweeUsername(final String cursorId) {
    if (cursorId == null) {
      return null;
    }
    return friend.followee.username.lt(cursorId);
  }

}
