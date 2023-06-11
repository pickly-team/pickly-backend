package org.pickly.service.comment.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.comment.service.dto.CommentDTO;
import org.pickly.service.comment.service.dto.QCommentDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.pickly.service.bookmark.entity.QBookmark.bookmark;
import static org.pickly.service.category.entity.QCategory.category;
import static org.pickly.service.comment.entity.QComment.comment;
import static org.pickly.service.member.entity.QMember.member;

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
        .where(
            bookmark.member.id.eq(memberId),
            notDeleted()
        )
        .fetch()
        .stream()
        .collect(Collectors.toMap(
            tuple -> tuple.get(bookmark.id),
            tuple -> Optional.ofNullable(tuple.get(comment.count())).orElse(0L)
        ));
  }

  @Override
  public List<CommentDTO> findComments(Long memberId, Long bookmarkId) {
    return queryFactory
        .select(new QCommentDTO(comment, member.nickname, bookmark.title, category.name))
        .from(comment)
        .leftJoin(comment.member, member)
        .leftJoin(comment.bookmark, bookmark)
        .leftJoin(bookmark.category, category)
        .where(
            eqMemberId(memberId),
            eqBookmarkId(bookmarkId),
            notDeleted()
        )
        .orderBy(comment.id.desc())
        .fetch();
  }

  private BooleanExpression eqMemberId(final Long memberId) {
    if (memberId == null) {
      return null;
    }
    return comment.member.id.eq(memberId);
  }

  private BooleanExpression eqBookmarkId(final Long bookmarkId) {
    if (bookmarkId == null) {
      return null;
    }
    return comment.bookmark.id.eq(bookmarkId);
  }

  private BooleanExpression notDeleted() {
    return comment.deletedAt.isNull();
  }


}
