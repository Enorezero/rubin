package ru.enorezero.authservice.service;

import ru.enorezero.authservice.model.Role;

import java.util.Set;

public interface JwtService {
    void validateToken(final String token);
    String generateToken(String username, Set<Role> roles);
}
