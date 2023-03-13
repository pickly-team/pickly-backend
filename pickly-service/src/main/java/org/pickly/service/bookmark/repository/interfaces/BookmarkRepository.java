package org.pickly.service.bookmark.repository.interfaces;

import java.util.List;
import java.util.Optional;
import org.pickly.service.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  Optional<Bookmark> findOneById(Long id);

  @Query(value = "SELECT * FROM Bookmark WHERE category_id = :category_id"
      , nativeQuery = true)
  List<Bookmark> findBookmarkByCategoryId(@Param("category_id") Long categoryId);

}
