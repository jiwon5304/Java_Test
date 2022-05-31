package com.example.java_test.adapter.in.web;

import com.example.java_test.application.port.in.UserUseCase;
import com.example.java_test.domain.User;
import com.ktown4u.gms.company.adapter.in.web.UserRequest;
import com.ktown4u.gms.company.adapter.in.web.UserResponse;
import com.ktown4u.gms.company.adapter.in.web.UsersApi;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController implements UsersApi {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    // toResponse 메소드 생성
    private UserResponse toResponse(User user) {
        return Mappers.getMapper(UserResponseMapper.class).toResponse(user);
    }

    // 회원 생성
    @Override
    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        User user = userUseCase.createUser(userRequest);
        return ResponseEntity.ok(toResponse(user));
    }

    // 회원 조회
    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = userUseCase.getUsers().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // id로 회원 조회
    @Override
    public ResponseEntity<UserResponse> getUserById (Long id) {
        User user = userUseCase.getUserById(id);
        return ResponseEntity.ok(toResponse(user));
    }

    // 회원 수정
    @Override
    public ResponseEntity<UserResponse> updateUser(Long id, UserRequest userRequest) {
        User user = userUseCase.updateUser(id, userRequest);
        return ResponseEntity.ok(toResponse(user));
    }

    // 회원 삭제
    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        userUseCase.deleteUser(id);
        return new ResponseEntity<Void> (HttpStatus.OK);
    }
}
