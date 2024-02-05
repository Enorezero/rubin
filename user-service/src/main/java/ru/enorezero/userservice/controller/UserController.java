package ru.enorezero.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.enorezero.userservice.model.User;
import ru.enorezero.userservice.service.UserService;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.create(user);
        return ResponseEntity.ok("Пользователь создан");
    }

}
