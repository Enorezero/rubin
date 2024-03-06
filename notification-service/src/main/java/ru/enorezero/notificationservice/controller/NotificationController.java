package ru.enorezero.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.enorezero.notificationservice.dto.UserDto;
import ru.enorezero.notificationservice.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/custom")
    ResponseEntity<?> send(String email, String subject, String text){
        notificationService.sendCustomEmail(email, subject, text);
        return ResponseEntity.status(HttpStatus.OK).body("Сообщение на почту " + email + " .");
    }
}
