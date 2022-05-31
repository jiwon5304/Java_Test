package com.example.java_test.application.port.out;

import com.example.java_test.domain.User;

import java.util.List;

public interface LoadUserPort {
    List<User> loadAll();
}
