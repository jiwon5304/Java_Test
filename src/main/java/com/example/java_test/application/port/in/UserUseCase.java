package com.example.java_test.application.port.in;

import com.example.java_test.domain.User;
import com.ktown4u.gms.company.adapter.in.web.UserRequest;

import java.util.List;

public interface UserUseCase {

    // 회원 생성
    User createUser(UserRequest userRequest);

    List<User> getUsers();

    User getUserById(Long id);

    User updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
}
