package ru.enorezero.userservice.service;

import ru.enorezero.userservice.model.User;

public interface UserService {
    void create(User user);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    User update(User user);
    User delete(String email);
}
