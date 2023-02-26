package org.pickly.service.bookmark.repository.interfaces;

import org.pickly.service.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

}
