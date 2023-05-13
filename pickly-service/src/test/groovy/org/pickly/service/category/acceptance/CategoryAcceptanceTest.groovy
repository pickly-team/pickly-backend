package org.pickly.service.category.acceptance

import com.fasterxml.jackson.databind.ObjectMapper
import org.pickly.service.category.CategoryFactory
import org.pickly.service.category.dto.controller.CategoryRequestDTO
import org.pickly.service.category.dto.controller.CategoryUpdateRequestDTO
import org.pickly.service.category.entity.Category
import org.pickly.service.category.repository.interfaces.CategoryRepository
import org.pickly.service.member.MemberFactory
import org.pickly.service.member.entity.Member
import org.pickly.service.member.repository.interfaces.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryAcceptanceTest extends Specification {

//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    private MemberFactory memberFactory = new MemberFactory();
//    private CategoryFactory categoryFactory = new CategoryFactory();
//
//    def "하나의 카테고리를 생성한다"() {
//        given:
//        Member testMember = memberRepository.save(memberFactory.testMember());
//        String json = objectMapper.writeValueAsString(new CategoryRequestDTO(testMember.getId(), testMember.getName()));
//
//        when:
//        var request = mockMvc.perform(post("/api/categories")
//                .contentType(APPLICATION_JSON)
//                .content(json))
//
//        then:
//        request
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    def "카테고리를 수정한다"() {
//        given:
//        Member testMember = memberRepository.save(memberFactory.testMember());
//        Category category = categoryRepository.save(categoryFactory.testCategory(testMember));
//
//        String json = objectMapper.writeValueAsString(
//                new CategoryUpdateRequestDTO(
//                        !category.getIsAutoDeleteMode(),
//                        "update" + category.getName(),
//                        "update" + category.getEmoji())
//        );
//
//        when:
//        var request = mockMvc.perform(post("/api/categories/" + category.getId())
//                .contentType(APPLICATION_JSON)
//                .content(json))
//
//        then:
//        request
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
}
