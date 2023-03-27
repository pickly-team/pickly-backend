package org.pickly.service.bookmark;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.category.entity.Category;
import org.pickly.service.member.entity.Member;

public class BookmarkFactory {

  public Bookmark testBookmark(Member member, Category category) {
    return Bookmark.builder()
        .member(member)
        .category(category)
        .title("검은 그림자 내 안에 깨어나")
        .url("https://pickly.com")
        .isUserLike(true)
        .visibility(Visibility.SCOPE_PUBLIC)
        .readByUser(false)
        .build();
  }

  public Bookmark testBookmark(int number, Member member, Category category) {
    return Bookmark.builder()
        .member(member)
        .category(category)
        .title("검은 그림자 내 안에 깨어나")
        .url("https://pickly.com/" + number)
        .isUserLike(true)
        .visibility(Visibility.SCOPE_PUBLIC)
        .readByUser(false)
        .build();
  }

  public List<Bookmark> testBookmarks(int amount, Member member, Category category) {
    return IntStream.rangeClosed(1, amount)
        .mapToObj(i -> Bookmark.builder()
            .member(member)
            .category(category)
            .title("검은 그림자 내 안에 깨어나")
            .url("https://pickly.com/" + i)
            .isUserLike(true)
            .visibility(Visibility.SCOPE_PUBLIC)
            .readByUser(false)
            .build())
        .collect(Collectors.toList());
  }

}
