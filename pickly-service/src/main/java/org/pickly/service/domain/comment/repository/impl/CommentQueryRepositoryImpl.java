package org.pickly.service.domain.comment.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.domain.comment.dto.service.CommentDTO;
import org.pickly.service.domain.comment.dto.service.QCommentDTO;
import org.pickly.service.domain.comment.repository.interfaces.CommentQueryRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.pickly.service.domain.block.entity.QBlock.block;
import static org.pickly.service.domain.bookmark.entity.QBookmark.bookmark;
import static org.pickly.service.domain.category.entity.QCategory.category;
import static org.pickly.service.domain.comment.entity.QComment.comment;
import static org.pickly.service.domain.member.entity.QMember.member;

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
  public Map<Long, Long> findBookmarkCommentCntByMemberAndKeyword(final Long memberId, final String keyword) {
    return queryFactory
        .select(bookmark.id, comment.count().castToNum(Long.class))
        .from(bookmark)
        .leftJoin(comment).on(comment.bookmark.id.eq(bookmark.id))
        .groupBy(bookmark.id)
        .where(
            bookmark.member.id.eq(memberId),
            bookmark.title.containsIgnoreCase(keyword),
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
        .select(new QCommentDTO(comment, member, bookmark.title, category.name, bookmark.id
        ))
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

  @Override
  public List<CommentDTO> findCommentsWithoutBlock(Long memberId, Long bookmarkId) {
    return queryFactory
        .select(new QCommentDTO(comment, member, bookmark.title, category.name, bookmark.id
        ))
        .from(comment)
        .leftJoin(comment.member, member)
        .leftJoin(comment.bookmark, bookmark)
        .leftJoin(bookmark.category, category)
        .where(
            inBlockId(memberId),
            eqBookmarkId(bookmarkId),
            notDeleted()
        )
        .orderBy(comment.id.desc())
        .fetch();
  }

  @Override
  public void deleteByCategory(List<Long> categories, LocalDateTime now) {
    queryFactory
        .update(comment)
        .set(comment.deletedAt, now)
        .where(
            comment.bookmark.id.in(
                JPAExpressions.select(bookmark.id)
                    .from(bookmark)
                    .where(bookmark.category.id.in(categories))
            )
        )
        .execute();
  }

  private BooleanExpression inBlockId(final Long memberId) {
    if (memberId == null) {
      return null;
    }
    return comment.member.id.notIn(
        selectAllBlockee(memberId)
    );
  }

  private JPQLQuery<Long> selectAllBlockee(final Long memberId) {
    return JPAExpressions
        .select(block.blockee.id)
        .from(block)
        .where(block.blocker.id.eq(memberId));
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
