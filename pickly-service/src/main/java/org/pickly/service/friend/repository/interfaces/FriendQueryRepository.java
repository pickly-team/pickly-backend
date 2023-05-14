package org.pickly.service.friend.repository.interfaces;

import org.pickly.service.friend.service.dto.FollowerResDTO;

import java.util.List;

public interface FriendQueryRepository {

  List<FollowerResDTO> findAllFollowerByMember(Long memberId);

}
