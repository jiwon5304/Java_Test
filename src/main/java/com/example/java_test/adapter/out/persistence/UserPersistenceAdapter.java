package com.example.java_test.adapter.out.persistence;

import com.example.java_test.application.port.out.LoadUserPort;
import com.example.java_test.application.port.out.UpdateUserPort;
import com.example.java_test.domain.User;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserPersistenceAdapter implements LoadUserPort, UpdateUserPort {

    private final UserRepository userRepository;

    private final UserEntityMapper mapper;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
        mapper = Mappers.getMapper(UserEntityMapper.class);
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(userRepository.save(mapper.toEntity(user)));
    }

    @Override
    public List<User> loadAll() {
        return userRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return mapper.toDomain(userRepository.getById(id));
    }

    @Override
    public User update(User user) {
        return mapper.toDomain(userRepository.save(mapper.toEntity(user)));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
