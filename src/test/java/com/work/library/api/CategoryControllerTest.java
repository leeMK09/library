package com.work.library.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.library.api.base.ResponseMessage;
import com.work.library.api.base.ResponseType;
import com.work.library.application.CategorySearchApplication;
import com.work.library.application.dto.result.SearchCategoryResult;
import com.work.library.config.GlobalExceptionHandler;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@ContextConfiguration(classes = CategoryController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategorySearchApplication categorySearchApplication;

    @Nested
    class Read {
        @Test
        void 모든_카테고리_조회_성공시_200_응답을_반환한다() throws Exception {
            List<SearchCategoryResult> mockResults = List.of(
                    new SearchCategoryResult("문학"),
                    new SearchCategoryResult("IT"),
                    new SearchCategoryResult("인문학")
            );

            Mockito.when(categorySearchApplication.findAll())
                    .thenReturn(mockResults);

            mockMvc.perform(
                    get("/categories")
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type").value(ResponseType.SUCCESS.name()))
                    .andExpect(jsonPath("$.message").value(ResponseMessage.CategoryResponseMessage.SUCCESS_CATEGORIES_READ))
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andDo(document("categories-search",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            responseFields(
                                    fieldWithPath("type").type(STRING).description("응답 타입"),
                                    fieldWithPath("message").type(STRING).description("응답 메시지"),
                                    fieldWithPath("data.list[].name").type(STRING).description("카테고리 이름")
                            )
                    ));
        }
    }
}
