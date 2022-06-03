package com.example.java_test.adapter.in.web;

import com.example.java_test.application.service.UserService;
import com.example.java_test.domain.User;
import com.ktown4u.gms.company.adapter.in.web.UserRequest;
import com.ktown4u.gms.company.adapter.in.web.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private UserController controller;

    @Mock private UserService userService;

    @BeforeEach
    void setUp() {
        controller = new UserController(userService);
    }

    @Test
    @DisplayName("적합한 정보로 craeteUser가 호출되면 데이터베이스에 재대로 된 정보가 저장된다")
    public void createUser_successfully() throws Exception {
        // given
        UserRequest userRequest = new UserRequest();
        userRequest.setName("username");
        userRequest.setPassword("password");

        User user = new User();
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());

        when(userService.createUser(userRequest)).thenReturn(user);

        // when
        ResponseEntity<UserResponse> response = controller.createUser(userRequest);

        // then
        assertThat(response.getBody().getName()).isEqualTo(user.getName());
        verify(userService, times(1)).createUser(userRequest);

    }
}
