package org.pickly.service.member.service.interfaces;

import java.util.List;
import org.pickly.service.member.entity.Member;

public interface MemberService {

  void existsById(Long memberId);

  Member findById(Long id);

}
