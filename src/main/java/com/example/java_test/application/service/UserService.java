package com.example.java_test.application.service;

import com.example.java_test.application.port.in.UserUseCase;
import com.example.java_test.application.port.out.LoadUserPort;
import com.example.java_test.application.port.out.UpdateUserPort;
import com.example.java_test.domain.User;
import com.ktown4u.gms.company.adapter.in.web.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserUseCase {

    private final UpdateUserPort updateUserPort;
    private final LoadUserPort loadUserPort;

    public UserService(UpdateUserPort updateUserPort, LoadUserPort loadUserPort) {
        this.updateUserPort = updateUserPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public User createUser(UserRequest userRequest) {
        User user = User.createof(userRequest.getName(), userRequest.getPassword());
        return updateUserPort.save(user);
    }

    @Override
    public List<User> getUsers() {
        return loadUserPort.loadAll();
    }

    @Override
    public User getUserById(Long id) {
        return updateUserPort.findById(id);
    }

    @Override
    public User updateUser(Long id, UserRequest userRequest) {
        User user = User.updateof(id,userRequest.getName(), userRequest.getPassword());
        return updateUserPort.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        updateUserPort.delete(id);
    }

}
