package ru.enorezero.notificationservice.dto;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class UserDto {
    String username;
    String email;
    String password;
}