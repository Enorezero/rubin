package ru.enorezero.authservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.enorezero.authservice.model.User;
import ru.enorezero.authservice.producer.KafkaProducer;
import ru.enorezero.authservice.service.AuthService;
import ru.enorezero.authservice.service.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        producer.sendMessage(user);
    }
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
