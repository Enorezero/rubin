package ru.enorezero.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id, @RequestHeader("username") String username, @RequestHeader("email") String email){
        System.out.println(username);
        System.out.println(email);
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody User user){
        userService.update(user);
        return ResponseEntity.ok("Пользовательно обновлён");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return ResponseEntity.ok("Пользователь удален");
    }

}
