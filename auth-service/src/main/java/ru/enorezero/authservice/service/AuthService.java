package ru.enorezero.authservice.service;

import ru.enorezero.authservice.model.User;
import ru.enorezero.authservice.model.dto.AuthRequest;

public interface AuthService {
    void signUp(User user);
    String generateToken(AuthRequest request);
    void validateToken(String token);
}
