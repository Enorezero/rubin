package ru.enorezero.userservice.model.dto;

import lombok.Data;
import lombok.Value;
import ru.enorezero.userservice.model.Role;

import java.util.List;

@Data
@Value
public class UserDto {
    String username;
    String email;
    String password;
    List<Role> roles;
}
