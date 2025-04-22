package com.work.library.api;

import com.work.library.api.base.ResponseMessage;
import com.work.library.api.base.ResponseType;
import com.work.library.application.BookSearchApplication;
import com.work.library.application.dto.result.CategoryResult;
import com.work.library.application.dto.result.SearchBookResult;
import com.work.library.application.exception.ErrorType;
import com.work.library.config.GlobalExceptionHandler;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = BookController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookSearchApplication bookSearchApplication;

    @Nested
    class Read {
        @Test
        void 카테고리_ID_목록으로_도서_조회요청_성공시_200_응답을_한다() throws Exception {
            List<SearchBookResult> mockResults = List.of(
                    new SearchBookResult(1L, "JPA", "김영한", List.of(new CategoryResult("IT")))
            );
            Mockito.when(bookSearchApplication.searchByCategoryIdList(List.of(1L, 2L)))
                    .thenReturn(mockResults);

            mockMvc.perform(
                    get("/books")
                            .param("category-ids", "1", "2")
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type").value(ResponseType.SUCCESS.name()))
                    .andExpect(jsonPath("$.message").value(ResponseMessage.BookResponseMessage.SUCCESS_BOOK_READ))
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andDo(document("book-search-by-categories",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("category-ids").description("카테고리 ID 리스트 (예: 1,2)")
                        ),
                        responseFields(
                            fieldWithPath("type").type(STRING).description("응답 타입"),
                            fieldWithPath("message").type(STRING).description("응답 메시지"),
                            fieldWithPath("data.list[].id").type(NUMBER).description("도서 ID"),
                            fieldWithPath("data.list[].title").type(STRING).description("도서 제목"),
                            fieldWithPath("data.list[].author").type(STRING).description("도서 저자"),
                            fieldWithPath("data.list[].categories").type(ARRAY).description("도서의 카테고리 목록"),
                            fieldWithPath("data.list[].categories[].name").type(STRING).description("카테고리 이름")
                    )
            ));
        }

        @Test
        void 요청한_카테고리_ID_목록없이_요청한_경우_400_응답을_한다() throws Exception {
            mockMvc.perform(
                            get("/books")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ErrorType.INVALID_PARAMETER.name()))
                    .andExpect(jsonPath("$.message").value(ErrorType.INVALID_PARAMETER.getDescription()));
        }
    }
}