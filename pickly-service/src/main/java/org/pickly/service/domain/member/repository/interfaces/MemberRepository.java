package org.pickly.service.domain.member.repository.interfaces;

import org.pickly.service.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


  boolean existsByIdAndDeletedAtIsNull(Long id);

  boolean existsByEmailAndDeletedAtIsNull(String email);

  boolean existsByNicknameAndDeletedAtIsNull(String nickname);

  boolean existsByUsernameAndDeletedAtIsNull(String username);

  Optional<Member> findByIdAndDeletedAtIsNull(Long id);

  Optional<Member> findByEmailAndDeletedAtIsNull(String email);

  Optional<Member> findByEmail(String email);
}
