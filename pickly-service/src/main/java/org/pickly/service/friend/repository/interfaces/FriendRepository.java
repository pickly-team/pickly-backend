package org.pickly.service.friend.repository.interfaces;

import org.pickly.service.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRepository extends JpaRepository<Friend, Long> {

  @Modifying
  @Query("delete from Friend f where f.followee.id = :memberId and f.follower.id = :followerId")
  void unfollow(@Param("followerId") Long followerId, @Param("memberId") Long memberId);

  boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

}
