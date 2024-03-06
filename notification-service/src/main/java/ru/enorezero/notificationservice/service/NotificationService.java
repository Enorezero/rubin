package ru.enorezero.notificationservice.service;

import ru.enorezero.notificationservice.dto.UserDto;

public interface NotificationService {
    void sendRegistrationEmail(UserDto user);
    void sendCustomEmail(String email, String subject, String text);
}
