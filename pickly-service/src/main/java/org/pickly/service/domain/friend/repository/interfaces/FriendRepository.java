package org.pickly.service.domain.friend.repository.interfaces;

import java.util.Optional;
import org.pickly.service.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRepository extends JpaRepository<Friend, Long> {

  @Modifying
  @Query("delete from Friend f where f.followee.id = :memberId and f.follower.id = :followerId")
  void unfollow(@Param("followerId") Long followerId, @Param("memberId") Long memberId);

  boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

  Optional<Friend> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

  Long countByFollowerId(Long followerId);

  Long countByFolloweeId(Long followeeId);

}