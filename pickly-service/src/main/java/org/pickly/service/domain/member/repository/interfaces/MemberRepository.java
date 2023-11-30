package org.pickly.service.domain.member.repository.interfaces;

import org.pickly.service.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


  boolean existsByIdAndDeletedAtIsNull(Long id);

  boolean existsByEmailAndDeletedAtIsNull(String email);

  boolean existsByNicknameAndDeletedAtIsNull(String nickname);

  boolean existsByUsernameAndDeletedAtIsNull(String username);

  Optional<Member> findByIdAndDeletedAtIsNull(Long id);

  Optional<Member> findByEmailAndDeletedAtIsNull(String email);

  Optional<Member> findByEmail(String email);

  List<Member> findByStatusLastLoginAtBefore(LocalDateTime inactiveStandard);

  @Modifying
  @Query("update Member m set m.status.isInactive = true where m.id in :ids")
  void convertToSleeper(@Param("ids") List<Long> memberIds);

}
