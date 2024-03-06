package ru.enorezero.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.enorezero.userservice.model.User;
import ru.enorezero.userservice.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody User user){
        userService.add(user);
        return ResponseEntity.ok("Пользователь создан");
    }

    @GetMapping("/ids/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));
    }

    @GetMapping("/usernames/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByUsername(username));
    }

    @GetMapping("/emails/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByEmail(email));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody User user){
        userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body("Пользовательно обновлён");
    }

    @DeleteMapping("/ids/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Пользователь удален");
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody User user){
        userService.validate(user);
        return ResponseEntity.status(HttpStatus.OK).body("Валидные данные");
    }

}