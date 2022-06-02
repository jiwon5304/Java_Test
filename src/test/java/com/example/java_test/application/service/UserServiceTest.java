package com.example.java_test.application.service;

import com.example.java_test.application.port.out.LoadUserPort;
import com.example.java_test.application.port.out.UpdateUserPort;
import com.example.java_test.domain.User;
import com.ktown4u.gms.company.adapter.in.web.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService userService;
    @Mock private LoadUserPort loadUserPort;
    @Mock private UpdateUserPort updateUserPort;

    @BeforeEach
    void setUp() {
        userService = new UserService(updateUserPort, loadUserPort);
    }

    @Test
    void createUser_returns_newly_saved_company() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("username");
        userRequest.setPassword("password");

        User user = userService.createUser(userRequest);

        verify(updateUserPort, times(1)).save(any(User.class));
    }
}
