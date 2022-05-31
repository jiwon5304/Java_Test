package com.example.java_test.adapter.out.persistence;

import com.example.java_test.domain.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserEntityMapper {
    UserEntity toEntity(User user);

    User toDomain(UserEntity userEntity);

}