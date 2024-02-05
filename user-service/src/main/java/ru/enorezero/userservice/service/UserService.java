package ru.enorezero.userservice.service;

import ru.enorezero.userservice.model.User;

public interface UserService {
    void add(User user);
    User getById(Long id);
    User getByEmail(String email);
    void update(User user);
    void deleteById(Long id);
}
