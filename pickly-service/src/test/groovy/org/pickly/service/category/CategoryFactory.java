package org.pickly.service.category;

import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.member.entity.Member;

public class CategoryFactory {

  public Category testCategory(Member member) {
    return Category.builder()
        .member(member)
        .name("당장 내일 안으로 읽어야 하는 자바 면접 질문")
        .orderNum(1)
        .build();
  }

}
