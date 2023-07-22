package org.pickly.service.member.repository.interfaces;

import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.member.service.dto.SearchMemberResultResDTO;

import java.util.List;
import java.util.Map;

public interface MemberQueryRepository {

  Map<Long, String> findTokenByIds(List<Long> memberIds);
  List<SearchMemberResultResDTO> findAllMembersByKeyword(String keyword, Long memberId,
                                                         PageRequest pageRequest);

}
