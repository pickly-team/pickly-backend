package org.pickly.service.member.repository.interfaces;

import java.util.List;
import java.util.Map;

public interface MemberQueryRepository {

  Map<Long, String> findTokenByIds(List<Long> memberIds);

}
