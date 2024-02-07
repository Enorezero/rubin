package ru.enorezero.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.enorezero.authservice.model.User;
import ru.enorezero.authservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<?> signUp(@RequestBody User user){
        authService.signUp(user);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }

}
