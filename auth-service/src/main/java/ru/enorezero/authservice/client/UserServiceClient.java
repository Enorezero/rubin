package ru.enorezero.authservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class UserServiceClient {

    @Autowired
    private DiscoveryClient discoveryClient;

}
