package org.pickly.service.member.repository.interfaces;

import java.util.List;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.member.service.dto.SearchMemberResultResDTO;

public interface MemberQueryRepository {

  List<SearchMemberResultResDTO> findAllMembersByKeyword(String keyword, Long memberId,
      PageRequest pageRequest);
}
