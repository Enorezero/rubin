package ru.enorezero.userservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.enorezero.userservice.model.User;
import ru.enorezero.userservice.model.dto.UserDto;
import ru.enorezero.userservice.service.UserService;

@Component
public class KafkaConsumer {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    @SneakyThrows
    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message){
        UserDto userDto = objectMapper.readValue(message, UserDto.class);
        User user = modelMapper.map(userDto, User.class);
        userService.add(user);
    }
}
