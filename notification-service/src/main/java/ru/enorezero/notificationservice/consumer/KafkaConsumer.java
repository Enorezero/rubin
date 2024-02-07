package ru.enorezero.notificationservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.enorezero.notificationservice.dto.UserDto;
import ru.enorezero.notificationservice.service.NotificationService;

@Component
public class KafkaConsumer {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NotificationService notificationService;

    @SneakyThrows
    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message){
        UserDto userDto = objectMapper.readValue(message, UserDto.class);
        System.out.println(userDto.getEmail());
        notificationService.send(userDto.getEmail());
    }

}

