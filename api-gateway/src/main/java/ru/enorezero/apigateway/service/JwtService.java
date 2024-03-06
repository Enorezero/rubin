package ru.enorezero.apigateway.service;

import java.util.List;

public interface JwtService {
    void validateToken(final String token);
    String extractUsername(String token);
    List<String> extractRoles(String token);
}
