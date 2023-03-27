package org.pickly.service.bookmark.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.pickly.common.error.exception.EntityNotFoundException;
import org.pickly.service.bookmark.common.BookmarkMapper;
import org.pickly.service.bookmark.controller.request.BookmarkCreateReq;
import org.pickly.service.bookmark.controller.response.BookmarkRes;
import org.pickly.service.bookmark.dto.service.BookmarkItemDTO;
import org.pickly.service.bookmark.dto.service.BookmarkPreviewItemDTO;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.bookmark.repository.interfaces.BookmarkQueryRepository;
import org.pickly.service.bookmark.repository.interfaces.BookmarkRepository;
import org.pickly.service.bookmark.service.dto.BookmarkDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkListDeleteResDTO;
import org.pickly.service.bookmark.service.dto.BookmarkUpdateReqDTO;
import org.pickly.service.bookmark.service.interfaces.BookmarkService;
import org.pickly.service.category.entity.Category;
import org.pickly.service.category.exception.custom.CategoryNotFoundException;
import org.pickly.service.category.repository.interfaces.CategoryRepository;
import org.pickly.service.comment.repository.interfaces.CommentQueryRepository;
import org.pickly.service.common.utils.page.PageRequest;
import org.pickly.service.common.utils.page.PageResponse;
import org.pickly.service.member.entity.Member;
import org.pickly.service.member.exception.custom.MemberNotFoundException;
import org.pickly.service.member.repository.interfaces.MemberRepository;
import org.pickly.service.member.service.dto.MemberProfileUpdateDTO;
import org.pickly.service.member.service.interfaces.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

  private static final boolean USER_LIKE = true;
  private static final int LAST_ITEM = 1;
  private final BookmarkRepository bookmarkRepository;
  private final BookmarkQueryRepository bookmarkQueryRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final MemberService memberService;

  private final CategoryRepository categoryRepository;

  private final MemberRepository memberRepository;

  @Override
  public Long countMemberLikes(final Long memberId) {
    memberService.existsById(memberId);
    return bookmarkQueryRepository.count(memberId, USER_LIKE);
  }

  @Override
  public PageResponse<BookmarkItemDTO> findMemberLikeBookmarks(final PageRequest pageRequest,
      final Long memberId) {
    memberService.existsById(memberId);
    List<Bookmark> memberLikes = bookmarkQueryRepository.findBookmarks(pageRequest, memberId, null,
        USER_LIKE, null, null);
    return makeResponse(pageRequest.getPageSize(), memberLikes);
  }

  @Override
  public PageResponse<BookmarkPreviewItemDTO> findMemberBookmarks(
      final PageRequest pageRequest, final Long memberId, final Long categoryId,
      final Boolean readByUser, final Visibility visibility
  ) {
    memberService.existsById(memberId);
    List<Bookmark> memberBookmarks = bookmarkQueryRepository.findBookmarks(pageRequest, memberId,
        categoryId, null, readByUser, visibility);
    Map<Long, Long> bookmarkCommentCntMap = commentQueryRepository.findBookmarkCommentCntByMember(
        memberId);
    return makeResponse(pageRequest.getPageSize(), memberBookmarks, bookmarkCommentCntMap);
  }


  private <T> List<T> mapToDtoList(final List<Bookmark> bookmarks,
      final Function<Bookmark, T> mapper) {
    return bookmarks.stream().map(mapper).toList();
  }

  private PageResponse<BookmarkItemDTO> makeResponse(final int pageSize, List<Bookmark> bookmarks) {
    int contentSize = bookmarks.size();
    bookmarks = removeElement(bookmarks, contentSize, pageSize);
    List<BookmarkItemDTO> contents = mapToDtoList(bookmarks, BookmarkItemDTO::from);
    return new PageResponse<>(contentSize, pageSize, contents);
  }

  private PageResponse<BookmarkPreviewItemDTO> makeResponse(
      final int pageSize, List<Bookmark> bookmarks, final Map<Long, Long> commentCntMap) {
    int contentSize = bookmarks.size();
    bookmarks = removeElement(bookmarks, contentSize, pageSize);
    List<BookmarkPreviewItemDTO> contents = mapToDtoList(bookmarks,
        b -> BookmarkPreviewItemDTO.from(b, commentCntMap.get(b.getId())));
    return new PageResponse<>(contentSize, pageSize, contents);
  }

  private List<Bookmark> removeElement(final List<Bookmark> bookmarkList, final int size,
      final int pageSize) {
    if (size - LAST_ITEM >= pageSize) {
      List<Bookmark> resultList = new ArrayList<>(bookmarkList);
      resultList.remove(size - LAST_ITEM);
      return resultList;
    }
    return bookmarkList;
  }

  @Override
  public Bookmark findById(Long id) {
    bookmarkRepository.readByUser(id);
    return bookmarkRepository.findOneById(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 북마크입니다."));
  }

  @Override
  public Bookmark findByIdWithCategory(Long id) {
    return bookmarkRepository.findByIdWithCategory(id)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 북마크입니다."));
  }

  @Override
  public void likeBookmark(Long bookmarkId) {
    Bookmark bookmark = findById(bookmarkId);
    bookmark.like();
  }

  @Override
  public void cancelLikeBookmark(Long bookmarkId) {
    Bookmark bookmark = findById(bookmarkId);
    bookmark.deleteLike();
  }


  @Override
  @Transactional
  public BookmarkDeleteResDTO deleteBookmark(Long bookmarkId) {
    BookmarkDeleteResDTO bookmarkDeleteReqDTO = new BookmarkDeleteResDTO();
    bookmarkDeleteReqDTO.setIsDeleted(bookmarkRepository.deleteBookmarkById(bookmarkId));
    return bookmarkDeleteReqDTO;
  }


  @Override
  @Transactional
  public BookmarkListDeleteResDTO deleteBookmarks(List<Long> bookmarkIds) {
    BookmarkListDeleteResDTO bookmarkListDeleteResDTO = new BookmarkListDeleteResDTO();
    bookmarkListDeleteResDTO.setIsDeleted(bookmarkRepository.deleteBookmarksByIds(bookmarkIds));
    return bookmarkListDeleteResDTO;
  }

  @Override
  public Bookmark create(BookmarkCreateReq dto) {

    Category category = categoryRepository.findById(dto.getCategoryId())
        .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));
    Member member = memberRepository.findById(dto.getMemberId())
        .orElseThrow(() -> new MemberNotFoundException(dto.getMemberId()));
    Bookmark entity = new Bookmark(category, member, dto.getUrl(),
        dto.getTitle(), dto.getPreviewImageUrl(), dto.getIsUserLike(),
        dto.getReadByUser(), dto.getVisibility());

    return bookmarkRepository.save(entity);
  }


  @Override
  public PageResponse<BookmarkItemDTO> findBookmarkByCategoryId(final PageRequest pageRequest,
      final Long categoryId) {
    List<Bookmark> bookmarks = bookmarkQueryRepository.findBookmarkByCategoryId(pageRequest,
        categoryId);
    return makeResponse(pageRequest.getPageSize(), bookmarks);
  }

  @Transactional
  @Override
  public void updateBookmark(Long bookmarkId, BookmarkUpdateReqDTO request) {
    Bookmark bookmark = findById(bookmarkId);

    Category category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

    bookmark.updateBookmark(
        category,
        request.getTitle(),
        request.getReadByUser(),
        request.getVisibility()
    );
  }
}
