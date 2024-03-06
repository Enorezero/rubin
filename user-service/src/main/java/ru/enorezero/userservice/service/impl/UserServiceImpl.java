package ru.enorezero.userservice.service.impl;

import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(
            value = "UserService::getById",
            key = "#id"
    )
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
    public User getByUsername(String username) {
        Optional<User> foundUser = Optional.of(repository.findOptionalByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")));
        return foundUser.get();
    }

    @Override
    @CachePut(
            value = "UserService::getById",
            key = "#user.id"
    )
    public void update(User user) {
        Optional<User> foundUser = Optional.of(repository.findOptionalById(user.getId())
                .orElseThrow(() -> new NotFoundException("Не удалось обновить пользователя")));
        foundUser.get().setEmail(user.getEmail());
        foundUser.get().setUsername(user.getUsername());
        foundUser.get().setPassword(user.getPassword());
        repository.save(foundUser.get());
    }

    @Override
    @CacheEvict(
            value = "UserService::getById",
            key = "#id"
    )
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void validate(User user) {
        if(repository.existsUserByEmailAndUsername(user.getEmail(), user.getUsername())){
            throw new IllegalArgumentException("Пользователь с таким юзернеймом или почтой уже существует");
        }
    }

}