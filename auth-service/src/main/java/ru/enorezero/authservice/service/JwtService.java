package ru.enorezero.authservice.service;

public interface JwtService {
    void validateToken(final String token);
    String generateToken(String username);
}
