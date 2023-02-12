package org.pickly.service.member.repository.interfaces;

import org.pickly.service.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
