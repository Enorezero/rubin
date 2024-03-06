package ru.enorezero.notificationservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.enorezero.notificationservice.dto.UserDto;
import ru.enorezero.notificationservice.service.NotificationService;


@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;
    @Override
    public void sendRegistrationEmail(UserDto user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setSubject(user.getUsername() + ", cпасибо за регистрацию!");
        mailMessage.setText("Мы ценим каждого пользователя!");
        mailMessage.setTo(user.getEmail());
        mailSender.send(mailMessage);
    }

    @Override
    public void sendCustomEmail(UserDto user, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setSubject("Сообщение от администрации rubin.");
        mailMessage.setText(text);
        mailMessage.setTo(user.getEmail());
        mailSender.send(mailMessage);
    }
}
