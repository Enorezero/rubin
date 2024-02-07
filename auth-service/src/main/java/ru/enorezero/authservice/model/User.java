package ru.enorezero.authservice.model;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class User {
    String username;
    String email;
    String password;
}
