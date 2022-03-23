package com.soon83.springdatajpa.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon83.springdatajpa.domain.Gender;
import com.soon83.springdatajpa.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.soon83.springdatajpa.restdocs.ApiDocumentUtils.descriptionsForNameProperty;
import static com.soon83.springdatajpa.restdocs.ApiDocumentUtils.uriModifyingOperationPreprocessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Rollback(value = false)
@ExtendWith(RestDocumentationExtension.class)
class UserApiControllerIntegratedTest {

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUpRestdocs(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(uriModifyingOperationPreprocessor(), prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Order(2)
    @Test
    @DisplayName("[통합테스트] 사용자 목록 조회")
    void findAllUsers() throws Exception {
        // given

        // when

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("query-all-users",
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

    @Order(1)
    @Test
    @DisplayName("[통합테스트] 사용자 단건 조회")
    void findUserById() throws Exception {
        // given
        Long userId = 1L;

        // when

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.userId").isNotEmpty())
                .andExpect(jsonPath("data.userId").value(1L))
                .andExpect(jsonPath("data.name").value("드록바"))
                .andExpect(jsonPath("data.gender").value(Gender.MALE.name()))
                .andDo(print())
                .andDo(document("query-user-by-id",
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

    @Order(0)
    @Test
    @DisplayName("[통합테스트] 사용자 단건 등록")
    void createUser() throws Exception {
        // given
        UserCreateRequest userCreateRequest = new UserCreateRequest("드록바", Gender.MALE, 23);

        // when

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/users")
                        .content(objectMapper.writeValueAsString(userCreateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.userId").isNotEmpty())
                .andExpect(jsonPath("data.userId").value(1L))
                .andDo(print())

                .andDo(document("create-user",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(UserCreateRequest.class, "name"))),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("사용자 성별")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(UserCreateRequest.class, "gender"))),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("사용자 나이").optional()
                                        .attributes(key("constraints").value(descriptionsForNameProperty(UserCreateRequest.class, "age")))
                                //.attributes(key("constraints").value("0 이상인 값 이어야 합니다."))
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