package ru.enorezero.authservice.model;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    final String role;

    Role(String role){
        this.role = role;
    }
}

