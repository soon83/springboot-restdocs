package com.soon83.springdatajpa.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon83.springdatajpa.domain.Gender;
import com.soon83.springdatajpa.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.soon83.springdatajpa.restdocs.ApiDocumentUtils.getDocumentRequest;
import static com.soon83.springdatajpa.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(RestDocumentationExtension.class)
class UserApiControllerTest {

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("사용자 목록 조회")
    void findAllUsers() throws Exception {
        // given
        List<UserResponse> userResponses = Lists.newArrayList(
                new UserResponse(1L, "드록바", Gender.MALE, 23),
                new UserResponse(2L, "메시", Gender.FEMALE, 21)
        );

        // when
        when(userService.findAllUsers()).thenReturn(userResponses);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("query-all-users",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data.[0].userId").type(JsonFieldType.NUMBER).description("User 아이디"),
                                fieldWithPath("data.[0].name").type(JsonFieldType.STRING).description("User 이름"),
                                fieldWithPath("data.[0].gender").type(JsonFieldType.STRING).description("User 성별"),
                                fieldWithPath("data.[0].age").type(JsonFieldType.NUMBER).description("User 나이")
                        )
                ));
    }

    @Test
    @DisplayName("사용자 단건 조회")
    void findUserById() throws Exception {
        // given
        Long userId = 1L;
        UserResponse userResponse = new UserResponse(userId, "드록바", Gender.MALE, 23);

        // when
        when(userService.findUserById(eq(userId))).thenReturn(userResponse);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("query-user-by-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("userId").description("사용자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("User 아이디"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("User 이름"),
                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("User 성별"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("User 나이")
                        )
                ));
    }

    @Test
    @DisplayName("사용자 단건 등록")
    void createUser() throws Exception {
        // given
        Long createdUserId = 1L;
        UserDto userDto = new UserDto("드록바", Gender.MALE, 23);

        // when
        when(userService.createUser(any(UserDto.class))).thenReturn(createdUserId);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/users")
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("create-user",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                attributes(key("title").value("사용자 단건 등록")),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("User 이름"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("User 성별"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("User 나이")
                                        .attributes(key("constraints").value("얘는 비어있음 안돼요"))
                        ),
                        relaxedResponseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("결과"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("User 아이디")
                        )
                ));
    }
}