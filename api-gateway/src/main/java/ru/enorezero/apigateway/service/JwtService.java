package ru.enorezero.apigateway.service;

public interface JwtService {
    void validateToken(final String token);
    public String extractUsername(String token);
}
