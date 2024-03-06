package ru.enorezero.apigateway.filter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.enorezero.apigateway.service.JwtService;
import ru.enorezero.apigateway.validator.RouteValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class RoleAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<RoleAuthGatewayFilterFactory.Config> {

    private final String USERNAME_HEADER = "username";
    private final String BEARER_TYPE = "Bearer ";

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtService jwtService;

    public RoleAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = null;

            if (validator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return handleErrorRequest(exchange, HttpStatus.UNAUTHORIZED);
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authHeader != null && authHeader.startsWith(BEARER_TYPE)) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    if(validator.isAdministrative.test(exchange.getRequest())){
                        List<String> roles = jwtService.extractRoles(authHeader);
                        if(!roles.contains(config.getRole())){
                            return handleErrorRequest(exchange, HttpStatus.FORBIDDEN);
                        }
                    }

                    jwtService.validateToken(authHeader);
                }
                catch (ExpiredJwtException e){
                    return handleErrorRequest(exchange, HttpStatus.UNAUTHORIZED);
                }

                request = exchange.getRequest()
                        .mutate()
                        .header(USERNAME_HEADER, jwtService.extractUsername(authHeader))
                        .build();

            }
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    private Mono<Void> handleErrorRequest(ServerWebExchange exchange, HttpStatus status){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    @Data
    public static class Config {
        private String role;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("role");
    }
}
