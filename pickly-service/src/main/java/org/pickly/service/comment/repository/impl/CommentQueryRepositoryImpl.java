package org.pickly.service.comment.repository.impl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static org.pickly.service.bookmark.entity.QBookmark.bookmark;
import static org.pickly.service.comment.entity.QComment.comment;
import static org.pickly.service.member.entity.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Map<Long, Long> findBookmarkCommentCntByMember(Long memberId) {
    return queryFactory
        .from(comment)
        .rightJoin(comment.bookmark, bookmark)
        .rightJoin(bookmark.member, member)
        .where(member.id.eq(memberId))
        .transform(groupBy(bookmark.id).as(comment.count()));
  }

}
