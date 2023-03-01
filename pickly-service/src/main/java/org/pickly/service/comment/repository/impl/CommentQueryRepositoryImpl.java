package org.pickly.service.comment.repository.impl;

import static org.pickly.service.bookmark.entity.QBookmark.bookmark;
import static org.pickly.service.comment.entity.QComment.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Map<Long, Long> findBookmarkCommentCntByMember(Long memberId) {
    return queryFactory
        .select(bookmark.id, comment.count().castToNum(Long.class))
        .from(bookmark)
        .leftJoin(comment).on(comment.bookmark.id.eq(bookmark.id))
        .groupBy(bookmark.id)
        .where(bookmark.member.id.eq(memberId))
        .fetch()
        .stream()
        .collect(Collectors.toMap(
            tuple -> tuple.get(bookmark.id),
            tuple -> Optional.ofNullable(tuple.get(comment.count())).orElse(0L)
        ));
  }


}
