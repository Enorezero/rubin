package ru.enorezero.userservice.model;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    final String role;

    Role(String role){
        this.role = role;
    }
}

