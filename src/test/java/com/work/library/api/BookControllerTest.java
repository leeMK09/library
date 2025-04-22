package com.work.library.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.library.api.base.ResponseMessage;
import com.work.library.api.base.ResponseType;
import com.work.library.api.request.ChangeBookCategoriesRequest;
import com.work.library.api.request.RegisterBookRequest;
import com.work.library.application.BookCategoriesUpdateApplication;
import com.work.library.application.BookRegisterApplication;
import com.work.library.application.BookSearchApplication;
import com.work.library.application.dto.command.ChangeBookCategoriesCommand;
import com.work.library.application.dto.command.RegisterBookCommand;
import com.work.library.application.dto.result.CategoryResult;
import com.work.library.application.dto.result.BookResult;
import com.work.library.application.exception.ErrorType;
import com.work.library.config.GlobalExceptionHandler;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookSearchApplication bookSearchApplication;

    @MockitoBean
    private BookRegisterApplication bookRegisterApplication;

    @MockitoBean
    private BookCategoriesUpdateApplication bookCategoriesUpdateApplication;

    @Nested
    class Read {
        @Test
        void 카테고리_ID_목록으로_도서_조회요청_성공시_200_응답을_반환한다() throws Exception {
            List<BookResult> mockResults = List.of(
                    new BookResult(1L, "JPA", "김영한", List.of(new CategoryResult("IT")))
            );

            when(bookSearchApplication.searchByCategoryIds(List.of(1L, 2L)))
                    .thenReturn(mockResults);

            mockMvc.perform(
                    get("/v1/books/categories")
                            .param("ids", "1", "2")
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type").value(ResponseType.SUCCESS.name()))
                    .andExpect(jsonPath("$.message").value(ResponseMessage.BookResponseMessage.SUCCESS_BOOK_READ))
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andDo(document("book-search-by-categories-success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("ids").description("카테고리 ID 리스트 (예: 1,2)")
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
        void 요청시_카테고리_ID_목록없이_요청한_경우_400_응답을_반환한다() throws Exception {
            mockMvc.perform(
                            get("/v1/books/categories")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ErrorType.INVALID_PARAMETER.name()))
                    .andExpect(jsonPath("$.message").value(ErrorType.INVALID_PARAMETER.getDescription()))
                    .andDo(document("book-search-by-categories-failed",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            responseFields(
                                    fieldWithPath("type").type(STRING).description("응답 타입"),
                                    fieldWithPath("message").type(STRING).description("응답 메시지"),
                                    fieldWithPath("data").type(NULL).description("응답 데이터")
                            )
                    ));
        }

        @Test
        void 지은이와_제목으로_도서_조회요청_성공시_200_응답을_한다() throws Exception {
            List<BookResult> mockResults = List.of(
                    new BookResult(1L, "JPA", "김영한", List.of(new CategoryResult("IT")))
            );

            when(bookSearchApplication.searchByTileOrAuthor("JPA", "김영한"))
                    .thenReturn(mockResults);

            mockMvc.perform(
                    get("/v1/books")
                            .param("title", "JPA")
                            .param("author", "김영한")
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type").value(ResponseType.SUCCESS.name()))
                    .andExpect(jsonPath("$.message").value(ResponseMessage.BookResponseMessage.SUCCESS_BOOK_READ))
                    .andExpect(jsonPath("$.data.list").isArray())
                    .andDo(document("book-search-by-title-or-author",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            queryParameters(
                                    parameterWithName("title").description("책 제목 (예: JPA)"),
                                    parameterWithName("author").description("책 저자 (예: 김영한")
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
    }

    @Nested
    class Save {
        @Test
        void 올바른_요청을_통해_도서를_등록하면_200_응답을_반환한다() throws Exception {
            RegisterBookRequest request = new RegisterBookRequest("JPA", "김영한", List.of(1L));
            RegisterBookCommand command = new RegisterBookCommand("JPA", "김영한", List.of(1L));

            when(bookRegisterApplication.save(command))
                    .thenReturn(1L);

            mockMvc.perform(
                    post("/v1/books")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type").value(ResponseType.SUCCESS.name()))
                    .andExpect(jsonPath("$.message").value(ResponseMessage.BookResponseMessage.SUCCESS_BOOK_SAVE))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andDo(document("book-save-success",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                    fieldWithPath("title").type(STRING).description("도서 제목"),
                                    fieldWithPath("author").type(STRING).description("도서 저자"),
                                    fieldWithPath("categoryIds").type(ARRAY).description("카테고리 목록")
                            ),
                            responseFields(
                                    fieldWithPath("type").type(STRING).description("응답 타입"),
                                    fieldWithPath("message").type(STRING).description("응답 메시지"),
                                    fieldWithPath("data.id").type(NUMBER).description("등록된 도서 ID")
                            )
                    ));
        }

        @Test
        void 제목이_비어있다면_400_응답을_반환한다() throws Exception {
            RegisterBookRequest request = new RegisterBookRequest("", "김영한", List.of(1L));

            mockMvc.perform(
                    post("/v1/books")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ErrorType.INVALID_PARAMETER.name()))
                    .andExpect(jsonPath("$.message").value(ErrorType.INVALID_PARAMETER.getDescription()))
                    .andDo(document("book-save-failed-by-empty-title",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            responseFields(
                                    fieldWithPath("type").type(STRING).description("응답 타입"),
                                    fieldWithPath("message").type(STRING).description("응답 메시지"),
                                    fieldWithPath("data").type(NULL).description("응답 데이터")
                            )
                    ));
        }

        @Test
        void 저자가_비어있다면_400_응답을_반환한다() throws Exception {
            RegisterBookRequest request = new RegisterBookRequest("JPA", "", List.of(1L));

            mockMvc.perform(
                            post("/v1/books")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ErrorType.INVALID_PARAMETER.name()))
                    .andExpect(jsonPath("$.message").value(ErrorType.INVALID_PARAMETER.getDescription()))
                    .andDo(document("book-save-failed-by-empty-author",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            responseFields(
                                    fieldWithPath("type").type(STRING).description("응답 타입"),
                                    fieldWithPath("message").type(STRING).description("응답 메시지"),
                                    fieldWithPath("data").type(NULL).description("응답 데이터")
                            )
                    ));
        }

        @Test
        void 카테고리_목록이_null이면_400_응답을_반환한다() throws Exception {
            RegisterBookRequest request = new RegisterBookRequest("JPA", "", null);

            mockMvc.perform(
                            post("/v1/books")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ErrorType.INVALID_PARAMETER.name()))
                    .andExpect(jsonPath("$.message").value(ErrorType.INVALID_PARAMETER.getDescription()))
                    .andDo(document("book-save-failed-by-null-categories",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            responseFields(
                                    fieldWithPath("type").type(STRING).description("응답 타입"),
                                    fieldWithPath("message").type(STRING).description("응답 메시지"),
                                    fieldWithPath("data").type(NULL).description("응답 데이터")
                            )
                    ));
        }

        @Test
        void 빈_카테고리_목록이라면_400_응답을_반환한다() throws Exception {
            RegisterBookRequest request = new RegisterBookRequest("JPA", "", List.of());

            mockMvc.perform(
                            post("/v1/books")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ErrorType.INVALID_PARAMETER.name()))
                    .andExpect(jsonPath("$.message").value(ErrorType.INVALID_PARAMETER.getDescription()))
                    .andDo(document("book-save-failed-by-empty-categories",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            responseFields(
                                    fieldWithPath("type").type(STRING).description("응답 타입"),
                                    fieldWithPath("message").type(STRING).description("응답 메시지"),
                                    fieldWithPath("data").type(NULL).description("응답 데이터")
                            )
                    ));
        }

        @Nested
        class Update {
            @Test
            void 올바른_요청을_통해_도서의_카테고리를_변경하면_200_응답을_반환한다() throws Exception {
                ChangeBookCategoriesRequest request = new ChangeBookCategoriesRequest(List.of(1L, 2L));
                ChangeBookCategoriesCommand command = new ChangeBookCategoriesCommand(1L, List.of(1L, 2L));
                List<CategoryResult> categoryResults = List.of(
                        new CategoryResult("문학"),
                        new CategoryResult("IT")
                );
                BookResult mockResult = new BookResult(1L, "JPA", "김영한", categoryResults);

                when(bookCategoriesUpdateApplication.changeBookCategories(command))
                        .thenReturn(mockResult);

                mockMvc.perform(
                        patch("/v1/books/{bookId}/categories", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.type").value(ResponseType.SUCCESS.name()))
                        .andExpect(jsonPath("$.message").value(ResponseMessage.BookResponseMessage.SUCCESS_BOOK_CATEGORIES_CHANGE))
                        .andExpect(jsonPath("$.data.bookId").value(1L))
                        .andExpect(jsonPath("$.data.categories").isArray())
                        .andDo(document("book-categories-change-success",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("bookId").description("카테고리를 변경할 도서 ID")
                                ),
                                requestFields(
                                        fieldWithPath("ids").type(ARRAY).description("변경할 카테고리 ID 리스트")
                                ),
                                responseFields(
                                        fieldWithPath("type").type(STRING).description("응답 타입"),
                                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                                        fieldWithPath("data.bookId").type(NUMBER).description("도서 ID"),
                                        fieldWithPath("data.categories").type(ARRAY).description("변경된 카테고리 목록"),
                                        fieldWithPath("data.categories[].name").type(STRING).description("카테고리 이름")
                                )
                        ));
            }

            @Test
            void 카테고리_목록이_null이라면_400_응답을_반환한다() throws Exception {
                ChangeBookCategoriesRequest request = new ChangeBookCategoriesRequest(null);

                mockMvc.perform(
                        patch("/v1/books/{bookId}/categories", 1L)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.type").value(ErrorType.INVALID_PARAMETER.name()))
                        .andExpect(jsonPath("$.message").value(ErrorType.INVALID_PARAMETER.getDescription()))
                        .andDo(document("book-categories-change-failed-by-null-categories",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("type").type(STRING).description("응답 타입"),
                                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                                        fieldWithPath("data").type(NULL).description("응답 데이터")
                                )
                        ));
            }

            @Test
            void 카테고리_목록이_비어있다면_400_응답을_반환한다() throws Exception {
                ChangeBookCategoriesRequest request = new ChangeBookCategoriesRequest(List.of());

                mockMvc.perform(
                                patch("/v1/books/{bookId}/categories", 1L)
                                        .content(objectMapper.writeValueAsString(request))
                                        .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.type").value(ErrorType.INVALID_PARAMETER.name()))
                        .andExpect(jsonPath("$.message").value(ErrorType.INVALID_PARAMETER.getDescription()))
                        .andDo(document("book-categories-change-failed-by-empty-categories",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("type").type(STRING).description("응답 타입"),
                                        fieldWithPath("message").type(STRING).description("응답 메시지"),
                                        fieldWithPath("data").type(NULL).description("응답 데이터")
                                )
                        ));
            }
        }
    }
}
