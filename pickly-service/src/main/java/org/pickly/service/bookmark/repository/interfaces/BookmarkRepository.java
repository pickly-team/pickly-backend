package org.pickly.service.bookmark.repository.interfaces;

import java.util.Optional;
import org.pickly.service.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  @Query("select b from Bookmark b join fetch b.member m where b.id = :id")
  Optional<Bookmark> findOneById(@Param("id") Long id);

}
