package com.example.java_test.adapter.in.web;

import com.example.java_test.application.service.UserService;
import com.example.java_test.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktown4u.gms.company.adapter.in.web.UserRequest;
import com.ktown4u.gms.company.adapter.in.web.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
public class UserControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private UserService userService;

    // 회원 생성
    @Test
    @DisplayName("적합한 정보로 createUser가 호출되면 데이터베이스에 제대로 된 정보가 저장된다")
    public void createUser_successfully() throws Exception {
        // given
        UserRequest userRequest = new UserRequest();
        userRequest.setName("username");
        userRequest.setPassword("password");
        String jsonString = objectMapper.writeValueAsString(userRequest);

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setId(0l);
        expectedResponse.setName(userRequest.getName());
        expectedResponse.setPassword(userRequest.getPassword());

        User user = new User();
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());

        when(userService.createUser(userRequest)).thenReturn(user);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                post("/users")
                        .contentType("application/json")
                        .content(jsonString)
                )
                .andDo(print())
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expectedResponse));
        verify(userService, times(1)).createUser(userRequest);
    }

    // 전체 회원 조회
    @Test
    @DisplayName("getUsers가 호출되면 데이터베이스에서 전체회원정보가 조회된다")
    public void getUsers_successfully() throws Exception {
        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/users"))
                .andDo(print())
                .andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(userService, times(1)).getUsers();
    }
}
