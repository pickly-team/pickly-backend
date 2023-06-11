package org.pickly.service.category.acceptance


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

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
