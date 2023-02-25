package ua.pp.juna.authenticationservice.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.authenticationservice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
