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
    UserService service;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody User user){
        service.add(user);
        return ResponseEntity.ok("Пользователь создан");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/email/{email}}")
    public ResponseEntity<User> getByEmail(@PathVariable String email){
        return ResponseEntity.ok(service.getByEmail(email));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody User user){
        service.update(user);
        return ResponseEntity.ok("Пользовательно обновлён");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return ResponseEntity.ok("Пользователь удален");
    }

}
