package org.pickly.service.category;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.pickly.service.category.entity.Category;
import org.pickly.service.member.MemberFactory;
import org.pickly.service.member.entity.Member;

public class CategoryFactory {

  private final MemberFactory memberFactory = new MemberFactory();

  public Category testCategory(Member member) {
    return Category.builder()
        .member(member)
        .isAutoDeleteMode(false)
        .name("당장 내일 안으로 읽어야 하는 자바 면접 질문")
        .build();
  }


  public List<Category> testCategories(int amount, Member member) {
    return IntStream.rangeClosed(1, amount)
        .mapToObj(i -> Category.builder()
            .member(member)
            .isAutoDeleteMode(false)
            .name("당장 내일 안으로 읽어야 하는 자바 면접 질문" + i)
            .build())
        .collect(Collectors.toList());
  }
}
