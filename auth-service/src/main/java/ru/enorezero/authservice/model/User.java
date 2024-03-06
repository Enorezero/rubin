package ru.enorezero.authservice.model;

import lombok.Data;

import java.util.Set;

@Data
public class User {
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
