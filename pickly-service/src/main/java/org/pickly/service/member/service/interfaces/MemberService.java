package org.pickly.service.member.service.interfaces;

import java.util.List;
import org.pickly.service.member.entity.Member;

public interface MemberService {

  List<Member> getMembers();

  Member save(String username);

  void existsById(Long memberId);

}
