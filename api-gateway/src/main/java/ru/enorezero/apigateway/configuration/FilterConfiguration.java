package ru.enorezero.apigateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.enorezero.apigateway.filter.RoleAuthGatewayFilterFactory;

@Configuration
public class FilterConfiguration {

    @Bean
    RoleAuthGatewayFilterFactory getFilter(){
        return new RoleAuthGatewayFilterFactory();
    }
}
