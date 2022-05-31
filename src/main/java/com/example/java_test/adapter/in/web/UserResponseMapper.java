package com.example.java_test.adapter.in.web;

import com.example.java_test.domain.User;
import com.ktown4u.gms.company.adapter.in.web.UserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserResponseMapper {
    UserResponse toResponse(User user);
}

