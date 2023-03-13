package org.pickly.service.category.repository.interfaces;

import java.util.List;
import java.util.Optional;
import org.pickly.service.category.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    @EntityGraph(attributePaths = {"member"})
    Optional<Category> findById(Long id);

    @Modifying
    @Query("SELECT c FROM Category c WHERE c.id IN :ids")
    List<Category> findAllByCategoryId(@Param("ids") List<Long> ids);

    List<Category> findAllByMemberId(Long memberId);
    @Query(value = "SELECT COUNT(c.id) FROM Category c "
        + "WHERE c.member_id = :member_id AND c.deleted_at IS NULL "
        + "GROUP BY c.member_id", nativeQuery = true)
    Integer getCategoryCntByMemberId(@Param("member_id") Long memberId);
}
