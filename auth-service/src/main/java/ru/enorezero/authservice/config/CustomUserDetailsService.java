package ru.enorezero.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.enorezero.authservice.client.UserServiceClient;
import ru.enorezero.authservice.model.User;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userServiceClient.findByUsername(username).getBody());
        return user.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }
}
