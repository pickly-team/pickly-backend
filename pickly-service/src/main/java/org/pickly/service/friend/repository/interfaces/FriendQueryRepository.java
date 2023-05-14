package org.pickly.service.friend.repository.interfaces;

import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.friend.service.dto.FollowerResDTO;
import org.pickly.service.friend.service.dto.FollowingResDTO;

import java.util.List;

public interface FriendQueryRepository {

  List<FollowerResDTO> findAllFollowerByMember(Long memberId, PageRequest pageRequest);

  List<FollowingResDTO> findAllFollowingByMember(Long memberId, PageRequest pageRequest);

}
