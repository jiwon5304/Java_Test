package com.example.java_test.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private long id;
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public static User createof(String name, String password) {
        return new User(name, password);
    }

    public static User updateof(Long id,String name, String password) {
        return new User(id, name, password);
    }
}
