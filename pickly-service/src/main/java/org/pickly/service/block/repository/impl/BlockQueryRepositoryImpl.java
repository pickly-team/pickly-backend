package org.pickly.service.block.repository.impl;

import static org.pickly.service.block.entity.QBlock.block;
import static org.pickly.service.bookmark.entity.QBookmark.bookmark;
import static org.pickly.service.member.entity.QMember.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pickly.service.block.repository.interfaces.BlockQueryRepository;
import org.pickly.service.block.service.dto.BlockBookmarkDTO;
import org.pickly.service.block.service.dto.BlockMemberDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class BlockQueryRepositoryImpl implements BlockQueryRepository {

  private final JPAQueryFactory queryFactory;


  @Override
  @Transactional(readOnly = true)
  public List<BlockMemberDTO> getBlockedMembers(Long blockerId, Pageable page) {
    return queryFactory.select(Projections.fields(BlockMemberDTO.class,
            block.blockee.id,
            block.blockee.nickname,
            block.blockee.profileEmoji
        ))
        .from(block)
        .leftJoin(member).on(member.id.eq(block.blockee.id)).fetchJoin()
        .where(block.blocker.id.eq(blockerId)
            .and(block.bookmark.isNull()))
        .orderBy(block.createdAt.desc())
        .offset(page.getOffset())
        .limit(page.getPageSize())
        .fetch();
  }

  @Override
  @Transactional(readOnly = true)
  public List<BlockBookmarkDTO> getBlockedBookmarks(Long blockerId, Pageable page) {
    return queryFactory.select(Projections.fields(BlockBookmarkDTO.class,
            block.bookmark.id,
            block.bookmark.title,
            block.bookmark.previewImageUrl
        ))
        .from(block)
        .leftJoin(bookmark).on(bookmark.id.eq(block.bookmark.id)).fetchJoin()
        .where(block.blocker.id.eq(blockerId)
            .and(block.blockee.isNull()))
        .orderBy(block.createdAt.desc())
        .offset(page.getOffset())
        .limit(page.getPageSize())
        .fetch();
  }

}
