package org.pickly.service.category.repository.interfaces;

import org.pickly.service.category.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  @EntityGraph(attributePaths = {"member"})
  Optional<Category> findById(Long id);

  @Query("select c from Category c where c.member.id = :memberId and c.deletedAt is null order by c.orderNum desc limit 1")
  Category findLastCategoryByMemberId(@Param("memberId") Long memberId);

  @Query("select c from Category c where c.member.id = :memberId and c.deletedAt is null order by c.orderNum desc")
  List<Category> findAllCategoryByMemberId(@Param("memberId") Long memberId);

  @Modifying
  @Query("SELECT c FROM Category c WHERE c.id IN :ids and c.deletedAt is null")
  List<Category> findAllByCategoryId(@Param("ids") List<Long> ids);

  @Query(value = "SELECT COUNT(c.id) FROM Category c "
      + "WHERE c.member_id = :member_id AND c.deleted_at IS NULL "
      + "GROUP BY c.member_id", nativeQuery = true)
  Integer getCategoryCntByMemberId(@Param("member_id") Long memberod);
}
