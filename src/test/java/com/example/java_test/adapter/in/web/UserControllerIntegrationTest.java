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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        // userRequest 객체 생성 후 json 형태로 변환
        UserRequest userRequest = new UserRequest();
        userRequest.setName("username");
        userRequest.setPassword("password");
        String jsonString = objectMapper.writeValueAsString(userRequest);

        // userRequest 를 기반으로 expectedResponse 생성
        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setId(0l);
        expectedResponse.setName(userRequest.getName());
        expectedResponse.setPassword(userRequest.getPassword());

        // userRequest 를 기반으로 user 객체 생성
        User user = new User();
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());


        // when 메소드를 통해 하나의 메소드가 호출되었을 때 특정 값을 반환하라고 설정
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
        // verify : 해당 구문이 호출되었는지 체크
        verify(userService, times(1)).createUser(userRequest);
    }

    // 전체 회원 조회
    @Test
    @DisplayName("getUsers가 호출되면 데이터베이스에서 전체회원정보가 조회된다")
    public void getUsers_successfully() throws Exception {
        // given
        // expectedResponse 생성

        // users List 객체 생성
        List<User> users = new ArrayList<>();
        User user1 = new User("user1", "password");
        User user2 = new User("user2", "password");
        users.add(user1);
        users.add(user2);

        when(userService.getUsers()).thenReturn(users); // List<User>

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/users"))
                .andDo(print())
                .andReturn()
                .getResponse();

        // then
        // size 확인
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(userService, times(1)).getUsers();
    }

    // id 로 회원 조회
    @Test
    @DisplayName("회원 id로 getUserById가 호출되면 데이터베이스에서 해당 회원 정보가 조회된다")
    public void getUserById_successfullty() throws Exception {

        // given
        // userRequest 생성
        UserRequest userRequest = new UserRequest();
        userRequest.setId(1l);

        // user 객체 생성
        User user = new User();
        user.setId(1l);
        user.setName("user3");
        user.setPassword("password3");

        // userRequest 를 기반으로 expectedResponse 생성
        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setId(user.getId());
        expectedResponse.setName(user.getName());
        expectedResponse.setPassword(user.getPassword());


        when(userService.getUserById(userRequest.getId())).thenReturn(user);

        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/users/1"))
                .andDo(print())
                .andReturn()
                .getResponse();


        // then
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expectedResponse));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(userService, times(1)).getUsers();
    }
}
