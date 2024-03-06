package ru.enorezero.authservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.enorezero.authservice.client.UserServiceClient;
import ru.enorezero.authservice.model.User;
import ru.enorezero.authservice.model.dto.AuthRequest;
import ru.enorezero.authservice.producer.KafkaProducer;
import ru.enorezero.authservice.service.AuthService;
import ru.enorezero.authservice.service.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void signUp(User user) {
        ResponseEntity<?> response = userServiceClient.isExist(user);

        if(response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Пользователь с такими данными уже зарегистирован");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        producer.sendMessage(user);
    }
    public String generateToken(AuthRequest request) {
        return jwtService.generateToken(request.getUsername(), request.getRoles());
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
