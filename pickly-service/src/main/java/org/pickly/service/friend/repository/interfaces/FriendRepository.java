package org.pickly.service.friend.repository.interfaces;

import org.pickly.service.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
