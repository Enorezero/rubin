package ru.enorezero.userservice.service.impl;

import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.enorezero.userservice.model.User;
import ru.enorezero.userservice.repository.UserRepository;
import ru.enorezero.userservice.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Override
    public void add(User user) {
        repository.save(user);
    }
    @Override
    public User getById(Long id){
        Optional<User> foundUser = Optional.of(repository.findOptionalById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")));
        return foundUser.get();
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> foundUser = Optional.of(repository.findOptionalByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")));
        return foundUser.get();
    }

    @Override
    public void update(User user) {
        Optional<User> foundUser = Optional.of(repository.findOptionalById(user.getId())
                .orElseThrow(() -> new NotFoundException("Не удалось обновить пользователя")));
        foundUser.get().setEmail(user.getEmail());
        foundUser.get().setUsername(user.getUsername());
        foundUser.get().setPassword(user.getPassword());
        repository.save(foundUser.get());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
