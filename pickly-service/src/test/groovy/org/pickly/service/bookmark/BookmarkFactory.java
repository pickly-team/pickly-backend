package org.pickly.service.bookmark;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.category.CategoryFactory;
import org.pickly.service.category.entity.Category;
import org.pickly.service.member.MemberFactory;
import org.pickly.service.member.entity.Member;

public class BookmarkFactory {

  private final MemberFactory memberFactory = new MemberFactory();

  private final CategoryFactory categoryFactory = new CategoryFactory();

  public Bookmark testBookmark() {
    Member member = memberFactory.testMember();
    return Bookmark.builder()
        .category(categoryFactory.testCategory(member))
        .title("검은 그림자 내 안에 깨어나")
        .url("https://pickly.com")
        .isUserLike(true)
        .visibility(Visibility.SCOPE_PUBLIC)
        .build();
  }

  public Bookmark testBookmark(int number) {
    Member member = memberFactory.testMember();
    return Bookmark.builder()
        .category(categoryFactory.testCategory(member))
        .title("검은 그림자 내 안에 깨어나")
        .url("https://pickly.com/" + number)
        .isUserLike(true)
        .visibility(Visibility.SCOPE_PUBLIC)
        .build();
  }

  public List<Bookmark> testBookmarks(int amount) {
    Member member = memberFactory.testMember();
    return IntStream.rangeClosed(1, amount)
        .mapToObj(i -> Bookmark.builder()
            .category(categoryFactory.testCategory(member))
            .title("검은 그림자 내 안에 깨어나")
            .url("https://pickly.com/" + i)
            .isUserLike(true)
            .visibility(Visibility.SCOPE_PUBLIC)
            .build())
        .collect(Collectors.toList());
  }

}
