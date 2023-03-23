package org.pickly.service.member.repository.interfaces;

import java.util.Optional;
import org.pickly.service.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


  boolean existsByIdAndDeletedAtIsNull(Long id);

  Optional<Member> findByIdAndDeletedAtIsNull(Long id);
}
