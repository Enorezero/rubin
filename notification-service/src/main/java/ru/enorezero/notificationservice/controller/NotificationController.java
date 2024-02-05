package ru.enorezero.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.enorezero.notificationservice.service.NotificationService;

import java.util.Properties;

@RestController
public class NotificationController {

    @Autowired
    NotificationService service;

    @GetMapping("/notification/{email}")
    void test(@PathVariable String email){
        service.send(email);
    }
}
