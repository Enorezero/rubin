package ru.enorezero.authservice.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.enorezero.authservice.model.User;

@Component
public class KafkaProducer {

    @Value("${topic.name}")
    private String topic;

    @Autowired
    private KafkaTemplate<String,String> template;
    @Autowired
    private ObjectMapper objectMapper;


    @SneakyThrows
    public void sendMessage(User user){
        String userAsMessage = objectMapper.writeValueAsString(user);
        template.send(topic, userAsMessage);
    }


}
