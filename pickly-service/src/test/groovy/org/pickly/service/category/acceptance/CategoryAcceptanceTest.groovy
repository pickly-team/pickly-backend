package org.pickly.service.category.acceptance

import com.fasterxml.jackson.databind.ObjectMapper
import org.pickly.service.category.CategoryFactory
import org.pickly.service.category.dto.controller.CreateCategoryReq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryAcceptanceTest extends Specification {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private CategoryFactory categoryFactory;

    def "하나의 카테고리를 생성한다"() {
        given:
        String json = objectMapper.writeValueAsString(new CreateCategoryReq());
//        String json = objectMapper.writeValueAsString(categoryFactory.testCategory());

        when:
        var request = mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

        then:
        request
                .andExpect(status().isOk())
                .andDo(print());
    }

    def "다수의 카테고리를 생성한다"() {
        given:
        String json = objectMapper.writeValueAsString(new CreateCategoryReq());
//        String json = objectMapper.writeValueAsString(categoryFactory.testCategories());

        when:
        var request = mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

        then:
        request
                .andExpect(status().isOk())
                .andDo(print());
    }

    def "카테고리를 수정한다"() {
        given:
        String json = objectMapper.writeValueAsString(new CreateCategoryReq());

        when:
        var request = mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        then:
        request
                .andExpect(status().isOk())
                .andDo(print());
    }
}
