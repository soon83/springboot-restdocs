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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApiController.class)
//@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class UserApiControllerTest {

    @MockBean
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

    @Test
    @DisplayName("[RestDocs] ????????? ?????? ??????")
    void findAllUsers() throws Exception {
        // given
        List<UserResponse> userResponses = Lists.newArrayList(
                new UserResponse(1L, "?????????", Gender.MALE, 23),
                new UserResponse(2L, "??????", Gender.FEMALE, 21)
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
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("???????????????"),
                                fieldWithPath("data.[0].userId").type(JsonFieldType.NUMBER).description("User ?????????"),
                                fieldWithPath("data.[0].name").type(JsonFieldType.STRING).description("User ??????"),
                                fieldWithPath("data.[0].gender").type(JsonFieldType.STRING).description("User ??????"),
                                fieldWithPath("data.[0].age").type(JsonFieldType.NUMBER).description("User ??????")
                        )
                ));
    }

    @Test
    @DisplayName("[RestDocs] ????????? ?????? ??????")
    void findUserById() throws Exception {
        // given
        Long userId = 1L;
        UserResponse userResponse = new UserResponse(userId, "?????????", Gender.MALE, 23);

        // when
        when(userService.findUserById(eq(userId))).thenReturn(userResponse);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("query-user-by-id",
                        pathParameters(
                                parameterWithName("userId").description("????????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("???????????????"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("User ?????????"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("User ??????"),
                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("User ??????"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("User ??????")
                        )
                ));
    }

    @Test
    @DisplayName("[RestDocs] ????????? ?????? ??????")
    void createUser() throws Exception {
        // given
        Long createdUserId = 1L;
        UserCreateRequest userCreateRequest = new UserCreateRequest("?????????", Gender.MALE, 23);

        // when
        when(userService.createUser(any(UserCreateRequest.class))).thenReturn(createdUserId);

        // then
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/users")
                        .content(objectMapper.writeValueAsString(userCreateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("create-user",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("????????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(UserCreateRequest.class, "name"))),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("????????? ??????")
                                        .attributes(key("constraints").value(descriptionsForNameProperty(UserCreateRequest.class, "gender"))),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("????????? ??????").optional()
                                        .attributes(key("constraints").value(descriptionsForNameProperty(UserCreateRequest.class, "age")))
                                //.attributes(key("constraints").value("0 ????????? ??? ????????? ?????????."))
                        ),
                        relaxedResponseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("??????"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("???????????????"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("User ?????????")
                        )
                ));
    }
}