package org.pickly.service.domain.friend.repository.interfaces;

import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.domain.friend.dto.service.FollowerResDTO;
import org.pickly.service.domain.friend.dto.service.FollowingResDTO;

import java.util.List;

public interface FriendQueryRepository {

  List<FollowingResDTO> findAllFollowingByMember(Long memberId, PageRequest pageRequest);

  List<FollowerResDTO> findAllFollowerWithOutBlockByMember(Long memberId, PageRequest pageRequest);

}
