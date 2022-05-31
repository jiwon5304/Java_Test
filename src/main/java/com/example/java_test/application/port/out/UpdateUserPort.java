package com.example.java_test.application.port.out;

import com.example.java_test.domain.User;

public interface UpdateUserPort {
    User save(User user);

    User findById(Long id);

    User update(User user);

    void delete(Long id);
}

