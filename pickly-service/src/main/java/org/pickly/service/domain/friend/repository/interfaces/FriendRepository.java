package org.pickly.service.domain.friend.repository.interfaces;

import org.pickly.service.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

  @Modifying
  @Query("delete from Friend f where f.followee.id = :targetId and f.follower.id = :followerId")
  void unfollow(@Param("followerId") Long fromMemberId, @Param("targetId") Long toMemberId);

  boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

  Optional<Friend> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

  Long countByFollowerId(Long followerId);

  Long countByFolloweeId(Long followeeId);

}
