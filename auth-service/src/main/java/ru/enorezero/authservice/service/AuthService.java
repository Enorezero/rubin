package ru.enorezero.authservice.service;

import ru.enorezero.authservice.model.User;

public interface AuthService {
    void signUp(User user);
}
