package ru.enorezero.authservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.enorezero.authservice.model.User;
import ru.enorezero.authservice.producer.KafkaProducer;
import ru.enorezero.authservice.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    KafkaProducer producer;
    @Override
    public void signUp(User user) {
        producer.sendMessage(user);
    }
}
