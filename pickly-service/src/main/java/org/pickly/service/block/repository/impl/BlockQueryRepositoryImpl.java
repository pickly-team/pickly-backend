package org.pickly.service.block.repository.impl;

import static org.pickly.service.block.entity.QBlock.block;
import static org.pickly.service.bookmark.entity.QBookmark.bookmark;
import static org.pickly.service.member.entity.QMember.member;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.block.repository.interfaces.BlockQueryRepository;
import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;
import org.pickly.service.common.utils.page.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class BlockQueryRepositoryImpl implements BlockQueryRepository {

  private final JPAQueryFactory queryFactory;

  private static final long CHECK_LAST = 1;

  @Override
  @Transactional(readOnly = true)
  public List<BlockMemberDTO> getBlockedMembers(Long blockerId, PageRequest pageRequest) {
    String cursor = (String) pageRequest.getCursorId();
    Integer size = pageRequest.getPageSize();

    return queryFactory.select(Projections.fields(BlockMemberDTO.class,
            block.blockee.id,
            block.blockee.nickname,
            block.blockee.profileEmoji
        ))
        .from(block)
        .leftJoin(member).on(member.id.eq(block.blockee.id))
        .where(
            block.blocker.id.eq(blockerId),
            gtblockeeNickname(cursor),
            block.bookmark.isNull()
        )
        .orderBy(block.blockee.id.asc())
        .limit(size + CHECK_LAST)
        .fetch();
  }

  @Override
  @Transactional(readOnly = true)
  public List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, PageRequest pageRequest) {
    String cursor = (String) pageRequest.getCursorId();
    Integer size = pageRequest.getPageSize();

    return queryFactory.select(Projections.fields(BlockBookmarkDTO.class,
            block.bookmark.id,
            block.bookmark.title,
            block.bookmark.previewImageUrl
        ))
        .from(block)
        .leftJoin(bookmark).on(bookmark.id.eq(block.bookmark.id))
        .where(
            block.blocker.id.eq(blockerId),
            gtbookmark(cursor),
            block.blockee.isNull()
        )
        .orderBy(block.bookmark.id.asc())
        .limit(size + CHECK_LAST)
        .fetch();
  }

  private BooleanExpression gtblockeeNickname(final String cursorId) {
    if (cursorId == null) {
      return null;
    }
    return block.blockee.nickname.gt(cursorId);
  }

  private BooleanExpression gtbookmark(final String cursorId) {
    if (cursorId == null) {
      return null;
    }
    return block.bookmark.id.gt(Long.valueOf(cursorId));
  }
}
