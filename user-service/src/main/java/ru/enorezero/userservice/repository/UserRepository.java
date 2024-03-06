package ru.enorezero.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.enorezero.userservice.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findOptionalById(Long id);
    Optional<User> findOptionalByEmail(String email);
    Optional<User> findOptionalByUsername(String username);
    boolean existsUserByEmailAndUsername(String email, String username);
    void deleteById(Long id);
}

