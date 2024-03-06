package ru.enorezero.authservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.enorezero.authservice.model.User;

@Component
public class UserServiceClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<User> findByUsername(String username){
        ServiceInstance instance = discoveryClient.getInstances("user-service")
                .stream().findAny()
                .orElseThrow(() -> new IllegalStateException("User-service не доступен"));
        String uri = instance.getUri().toString() + "/users/usernames/" + username;
        return restTemplate.getForEntity(uri, User.class, username);
    }

    public ResponseEntity<String> isExist(User user){
        ServiceInstance instance = discoveryClient.getInstances("user-service")
                .stream().findAny()
                .orElseThrow(() -> new IllegalStateException("User-service не доступен"));
        String uri = instance.getUri().toString() + "/users/validate";
        return restTemplate.postForEntity(uri, user, String.class);
    }

}
