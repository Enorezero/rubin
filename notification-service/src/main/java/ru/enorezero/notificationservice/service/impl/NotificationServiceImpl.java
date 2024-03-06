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
        sendEmail(user.getEmail(), user.getUsername() + ", спасибо за регистрацию!", "Мы ценим каждого пользователя!");
    }

    @Override
    public void sendCustomEmail(String email, String subject, String text) {
        sendEmail(email, subject, text);
    }

    private void sendEmail(String email, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setTo(email);
        System.out.println(mailMessage);
        mailSender.send(mailMessage);
    }
}
